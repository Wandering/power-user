package com.power.wechat.controller.order;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.power.context.UserContext;
import com.power.core.exception.BizException;
import com.power.domain.*;
import com.power.dto.UserInfoDTO;
import com.power.dto.WxEvent;
import com.power.enums.PowerEvent;
import com.power.facade.IOrderLineFacade;
import com.power.facade.IOrdersFacade;
import com.power.facade.IUserAcountsFacade;
import com.power.wechat.listener.IEventListener;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/29.
 * 发送模板消息
 */
@RestController
@RequestMapping("/pay/wechat")
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private IOrdersFacade ordersFacade;

    @Autowired
    private IUserAcountsFacade userAcountsFacade;

    @Autowired
    private IOrderLineFacade orderLineFacade;

    @Autowired
    private IEventListener listener;

    private final static ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    static {
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.initialize();
    }


    /**
     * <pre>
     *   用户充值
     *
     * </pre>
     * @param uniqueKey
     * @param fee  充值费用
     * @return
     */
    @PostMapping("/{uniqueKey}/balance")
    public WxPayUnifiedOrderResult balance(@PathVariable String uniqueKey,@RequestParam String fee){
        logger.info("**********************************生成订单表信息S********************************************");
        String orderNo = UUID.randomUUID().toString();
        UserInfoDTO userInfoDTO = UserContext.getCurrentUser();
        Orders order  = new Orders();
        order.setAgency(1L);
        order.setStatus(OrderStatus.PENDING.toString());
        order.setOrderOwner(userInfoDTO.getAccountId());
        order.setOrderNo(orderNo);
        //精度丢失
        order.setTotalFee(new BigDecimal(fee).doubleValue());
        //添加目前剩余金额 //初始金额等于充值金额
        order.setSurplusFee(new BigDecimal(fee).doubleValue());
        order.setType( OrderType.BALANCE.name());
//        order.setUserRoles(account.getRoles());
        ordersFacade.getMainService().create(order);
        OrderLine line = new OrderLine();
        line.setAgency(1L);
        line.setFee(new BigDecimal(fee).doubleValue());
        line.setFeeType(FeeType.BALANCE.toString());
        line.setOrderId(order.getId());
        line.setUserId(userInfoDTO.getAccountId());
        orderLineFacade.getMainService().create(line);
        /**生成本地订单**/
        logger.info("\n当前用户信息为:{}\n订单信息为:{}\n订单拓展为:{}",JSON.toJSONString(userInfoDTO),JSON.toJSONString(order),JSON.toJSONString(line));
        logger.info("**********************************生成订单表信息E********************************************");

        /**生成本地订单**/
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.builder().outTradeNo(orderNo).openid(userInfoDTO.getOpenId()).totalFee(WxPayUnifiedOrderRequest.yuanToFee(fee)).build();
        logger.info("微信下单对象组装完成：{}", JSON.toJSONString(wxPayUnifiedOrderRequest));
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = null;
        try {
            wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
        } catch (WxErrorException e) {
            logger.info(e.getMessage());
            logger.info("微信下单失败!:{}",e.getError().getErrorMsg());
            throw new BizException(ERRORCODE.UNIFIED_ORDER_FAIL.getCode(),e.getError().getErrorMsg());
        }
        logger.info("微信反馈信息为:{}",JSON.toJSONString(wxPayUnifiedOrderResult));
        return wxPayUnifiedOrderResult;
    }

    /**
     * <pre>
     *      用户退款,要求用户必须在登录情况下调用
     * </pre>
     * @author 杨永平
     * @param uniqueKey 公众号唯一标识
     * @return
     * @exception  BizException{
     * @see com.power.domain.ERRORCODE#UNIFIED_BALANCE_ZORE,
     * @see com.power.domain.ERRORCODE#UNIFIED_BALANCE_ORDER_ZORE,
     * @see com.power.domain.ERRORCODE#UNIFIED_PENDING_ORDER_NOT_ZORE,
     * @see com.power.domain.ERRORCODE#UNIFIED_EXIST,
     * }
     * @see  com.power.wechat.listener.event.RefundAcceptMsgEvent
     * @see com.power.wechat.listener.event.RefundEvent
     */
    @PostMapping("/{uniqueKey}/refund")
    public boolean refund(@PathVariable String uniqueKey){
        String openId = UserContext.getCurrentUser().getOpenId();
         Long accountId = UserContext.getCurrentUser().getAccountId();
        logger.info("收到用户退款请求，当前用户openId:{},当前公众号:{}",openId,uniqueKey);

        UserAcounts userAcounts = userAcountsFacade.getUserAccountsByAccountId(accountId);
        if (userAcounts.getDeposit() != null && userAcounts.getDeposit()>0){
            //用户有正在进行的退款
            throw new BizException(ERRORCODE.UNIFIED_EXIST.getCode(),ERRORCODE.UNIFIED_EXIST.getMessage());
        }
        if (userAcounts.getBalance()==0){
            //用户没有余额
            throw new BizException(ERRORCODE.UNIFIED_BALANCE_ZORE.getCode(),ERRORCODE.UNIFIED_BALANCE_ZORE.getMessage());
        }
        if (ordersFacade.countBalanceOrder(accountId)==0){
            //用户没有可退订单
            throw new BizException(ERRORCODE.UNIFIED_BALANCE_ORDER_ZORE.getCode(),ERRORCODE.UNIFIED_BALANCE_ORDER_ZORE.getMessage());
        }
        if (ordersFacade.countPendingOrder(accountId)!=0){
            //用户没有已经清算的订单
            throw new BizException(ERRORCODE.UNIFIED_PENDING_ORDER_NOT_ZORE.getCode(),ERRORCODE.UNIFIED_PENDING_ORDER_NOT_ZORE.getMessage());
        }
        //锁定到退款，清空原有的余额
        userAcountsFacade.lockBalance(userAcounts);
        UserInfoDTO userInfoDTO = UserContext.getCurrentUser();
        executor.submit(()->{
            WxEvent wxEvent = new WxEvent();
            wxEvent.setOpenId(openId);
            wxEvent.setUniqueKey(uniqueKey);
            wxEvent.setUserInfoDTO(userInfoDTO);
            wxEvent.setEvent(PowerEvent.ORDER_REFUND);
            listener.eventDispatched(wxEvent);
        });
        return true;
    }


//    public static void main(String[] args) {
//        OrderType.BALANCE.toString();
//    }
}
