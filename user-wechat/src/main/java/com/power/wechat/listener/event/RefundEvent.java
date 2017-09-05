package com.power.wechat.listener.event;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.domain.OrderStatus;
import com.power.domain.OrderType;
import com.power.domain.Orders;
import com.power.dto.*;
import com.power.enums.PowerEvent;
import com.power.facade.*;
import com.power.service.IOrdersService;
import com.power.wechat.listener.IEventListener;
import com.power.yuneng.activity.api.IActivityNotify;
import com.power.yuneng.user.IArticleService;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class RefundEvent implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(RefundEvent.class);
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private IOrdersFacade ordersFacade;
    @Autowired
    private IEventListener listener;

    @Autowired
    private IUserAcountsFacade userAcountsFacade;
    @Autowired
    private IUserOrderRefundFacade userOrderRefundFacade;

    public RefundEvent() {
    }

    /**
     * 用户退款事件
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        logger.info("*******************退款 S**********************");
        WxEvent wxEvent = (WxEvent) arg;
        UserInfoDTO userInfoDTO = wxEvent.getUserInfoDTO();
        logger.info("当前退款用户信息为:{}", JSON.toJSONString(userInfoDTO));
        List<Orders> ordersList = ordersFacade.findAllBalanceOrder(userInfoDTO.getAccountId());
        //预退款分组订单
        Map<String, List<Orders>> listMap = this.preRefundOrder(ordersList);
        //不能退款订单
        List<Orders> failList = listMap.get("failList");
        //允许退款订单
        List<Orders> successList = listMap.get("successList");
        //退款失败订单
        List<Orders> refundFailList = new ArrayList<>();
        //退款成功订单
        List<Orders> refundSuccessList = new ArrayList<>();
        //退款具体订单
        List<Orders> refundOrderList = new ArrayList<>();
        //尝试退款
        WxRefundEvent refundEvent = new WxRefundEvent();
        refundEvent.setUserInfoDTO(userInfoDTO);
        refundEvent.setUniqueKey(wxEvent.getUniqueKey());
        refundEvent.setOpenId(wxEvent.getOpenId());
        Integer totalFee = 0;
        for (Orders orders : successList) {

            String refundOrderNo = UUID.randomUUID().toString();
            boolean isRefund = wxRefund(orders, refundOrderNo);
            totalFee += WxPayRefundRequest.yuanToFee(orders.getSurplusFee().toString());
            Orders refundOrder = this.genRefundOrder(userInfoDTO.getAccountId(), refundOrderNo, orders);
            if (isRefund) {
                //标准反馈成功+业务反馈成功
                refundOrderList.add(refundOrder);
                refundSuccessList.add(orders);
            } else {
                refundFailList.add(refundOrder);
            }
        }

        if (refundSuccessList.size() > 0) {
            //有执行退款成功的订单给用户推送退款成功消息
            logger.info("用户accountId:{},用户昵称:{},用户退款成功！", userInfoDTO.getAccountId(), userInfoDTO.getNickname());
            logger.info("推送退款成功事件给用户！");
            refundEvent.setFee(totalFee);
            refundEvent.setEvent(PowerEvent.ORDER_REFUND_SUCCESS);
            listener.eventDispatched(refundEvent);
            //todo 用户退款表格维护
            ordersFacade.refundSuccess(refundSuccessList);
        } else {
            logger.info("用户accountId:{},用户昵称:{},用户退款失败！当前：\n 不可退款订单：{}\n可退款但退款失败订单：{}", userInfoDTO.getAccountId(), userInfoDTO.getNickname(), JSON.toJSONString(failList), JSON.toJSONString(refundFailList));
            refundEvent.setEvent(PowerEvent.ORDER_REFUND_FAIL);
            listener.eventDispatched(refundEvent);
        }
        if (refundOrderList.size() > 0) {
            //放置退款成功信息
            ordersFacade.saveRefundSuccessOrder(refundOrderList);
        }
        if (failList.size() > 0) {
            /**如果有数据和微信不统一，同步为微信订单,这里处理的是微信认为已经退款的订单 时间复杂度0(n)**/
            ordersFacade.syncRefund(failList);
        }
        if (refundFailList.size() > 0) {
            //如果有退款失败的订单 加入到定时任务 时间复杂度0(n平方)
            refundFailList.stream().forEach((obj) -> {
                obj.setStatus(OrderStatus.REFUND_FAIL.toString());
            });
            ordersFacade.saveRefundFailOrder(refundFailList);
            userOrderRefundFacade.createTimerTask(refundFailList);
        }
        logger.info("*******************退款 E**********************");
    }

    /**
     * 定时任务  定期清空退款失败队列
     * 目前暂定每日0点执行一次
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    public void refund() {
        //查询所有退款不成功用户
        List<UserOrderRefundDTO> userOrderRefundDTOList =  userOrderRefundFacade.findAllUser();
        Iterator<UserOrderRefundDTO> userOrderRefundDTOIterator = userOrderRefundDTOList.iterator();
        List<Orders> refundSuccessOrderList = new ArrayList<>();
        List<UserOrderRefundDTO> userOrderRefundSuccessDTOs = new ArrayList<>();


        while (userOrderRefundDTOIterator.hasNext()) {
            UserOrderRefundDTO userOrderRefundDTO = userOrderRefundDTOIterator.next();
            /**
             *             遍历对象
             *             这里认为被添加到队列里的订单一定是微信可退款订单，但因为余额不足等非错误原因而退款失败的订单
             *             时间复杂度为O(MN)
             */
            int success = 0;
            int fee = 0;
            for (UserOrderRefundDTO.UserOrder userOrder : userOrderRefundDTO.getUserOrders()) {
                boolean isRefund = wxRefund(userOrder.getOriginalOrder(), userOrder.getRefundOrder().getOrderNo());
                if (isRefund) {
                    //成功退款 添加到保存list统一处理 减少数据库io
                    refundSuccessOrderList.add(userOrder.getRefundOrder());
                    userOrderRefundSuccessDTOs.add(userOrderRefundDTO);
                    fee += WxPayRefundRequest.yuanToFee(userOrder.getRefundOrder().getTotalFee().toString());
                    success++;
                }

            }

            //当前用户的退款请求已经执行完毕 等待判定是否推送消息
            if (success>0){
                //退款成功
                WxRefundEvent refundEvent = new WxRefundEvent();
                refundEvent.setFee(fee);
                refundEvent.setEvent(PowerEvent.ORDER_REFUND_SUCCESS);
                listener.eventDispatched(refundEvent);
            }
        }
        ordersFacade.editAllRefundSuccess(userOrderRefundSuccessDTOs);
        userOrderRefundFacade.clear(userOrderRefundSuccessDTOs);

    }

    private boolean wxRefund(Orders orders,String refundOrderNo){
        Integer refundFee = WxPayRefundRequest.yuanToFee(orders.getSurplusFee().toString());
        WxPayRefundRequest wxPayRefundRequest =
                WxPayRefundRequest
                        .newBuilder()
                        .totalFee(WxPayRefundRequest.yuanToFee(orders.getTotalFee().toString()))
                        .refundFee(refundFee)
                        .outRefundNo(refundOrderNo)
                        .outTradeNo(orders.getOrderNo())
                        .build();

        WxPayRefundResult wxPayRefundResult = null;
        try {
            wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);

            if ("SUCCESS".equals(wxPayRefundResult.getReturnCode()) && "FAIL".equals(wxPayRefundResult.getResultCode()) && "NOTENOUGH".equals(wxPayRefundResult.getErrCode())) {
                logger.info("\n当前退款失败：\n订单号：{}\n订单id:{}\n当前用户accountId:{}\n当前反馈状态:{}", orders.getOrderNo(), orders.getId(), orders.getOrderOwner(), wxPayRefundResult.getErrCodeDes());
                logger.info("尝试使用可用余额退款！");
//                    REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
//                    REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
                wxPayRefundRequest.setRefundAccount("REFUND_SOURCE_RECHARGE_FUNDS");
                wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
            return false;
        }

        return "SUCCESS".equals(wxPayRefundResult.getReturnCode()) && "SUCCESS".equals(wxPayRefundResult.getResultCode());
    }

    /**
     * <pre>
     *     预退款，筛选允许退款的订单
     * </pre>
     *
     * @param ordersList 原始订单
     * @return
     */
    private Map<String, List<Orders>> preRefundOrder(List<Orders> ordersList) {
        //不能退款订单
        List<Orders> failList = new ArrayList<>();
        //允许退款订单
        List<Orders> successList = new ArrayList<>();
        for (Orders orders : ordersList) {
            try {
                WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(null, orders.getOrderNo());
                if ("SUCCESS".equals(wxPayOrderQueryResult.getReturnCode()) && "SUCCESS".equals(wxPayOrderQueryResult.getTradeState())) {
                    successList.add(orders);
                } else {
                    logger.info("\n当前订单号：{}\n订单id:{}\n当前用户accountId:{}\n金额:{}\n剩余金额;{}\n订单支付状态:{}", orders.getOrderNo(), orders.getId(), orders.getTotalFee(), orders.getSurplusFee(), wxPayOrderQueryResult.getTradeStateDesc());
                    failList.add(orders);
                }
            } catch (WxErrorException e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }
        logger.info("共计{}个可退订单，{}个不可退订单", successList.size(), failList.size());
        Map<String, List<Orders>> rtnMap = newHashMap();
        rtnMap.put("successList", successList);
        rtnMap.put("failList", failList);
        return rtnMap;
    }

    private Orders genRefundOrder(Long accountId,String refundOrderNo,Orders orders){
        Orders refundOrder = new Orders();
        refundOrder.setOrderNo(refundOrderNo);
        refundOrder.setEditor(accountId);
        //将退款置为负
        refundOrder.setTotalFee(-orders.getSurplusFee());
        refundOrder.setAgency(orders.getAgency());
        refundOrder.setType(OrderType.REFUND_BALANCE.toString());
        refundOrder.setStatus(OrderStatus.REFUND_BALANCE.toString());
        refundOrder.setSurplusFee(0D);
        refundOrder.setEditor(orders.getEditor());
        refundOrder.setOrderOwner(orders.getOrderOwner());
        refundOrder.setParentOrder(orders.getId());
        return refundOrder;
    }

}
