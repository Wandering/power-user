/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:03 $ 
 */
        package com.power.facade.impl;


import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.OrderStatus;
import com.power.domain.OrderType;
import com.power.domain.Orders;
import com.power.dto.UserOrderRefundDTO;
import com.power.facade.IOrdersFacade;
import com.power.service.IOrdersService;
import com.power.service.ex.IOrdersExService;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service("OrdersFacadeImpl")
public class OrdersFacadeImpl extends AbstractPersistenceProvider implements IOrdersFacade {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IOrdersExService ordersExService;

//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        ordersService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return ordersService;
    }

    /**
     * 查询用户未清算订单
     *
     * @param accountId
     * @return
     */
    @Override
    public List<Orders> findAllBalanceOrder(Long accountId) {
        //费用大于0的
        Map<String,Object> condition = new HashMap<>();
        condition.put("groupOp","AND");

        SearchField searchField = new SearchField();
        searchField.setField("surplusFee");
        searchField.setOp(SearchEnum.gt.getValue());
        searchField.setData(0);
        condition.put("surplusFee",searchField);

//        支付成功和正在扣费的
        searchField = new SearchField();
        searchField.setField("status");
        searchField.setOp(SearchEnum.in.getValue());
        searchField.setData(Arrays.asList("PAYED","IN_COUNT"));
        condition.put("status",searchField);
//        渠道类型
        searchField = new SearchField();
        searchField.setField("tradeType");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData("JSAPI");
        condition.put("tradeType",searchField);
//        类型
        searchField = new SearchField();
        searchField.setField("type");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(OrderType.BALANCE.toString());
        condition.put("type",searchField);
        //        类型
        searchField = new SearchField();
        searchField.setField("orderOwner");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(accountId);
        condition.put("orderOwner",searchField);
        return this.ordersService.viewList(null,condition,null);
    }

    /**
     * 退款订单
     *
     * @param refundSuccessList
     */
    @Override
    public void refundSuccess(List<Orders> refundSuccessList) {
        Iterator<Orders> ordersIterator = refundSuccessList.iterator();
        while (ordersIterator.hasNext()){
            Orders orders = ordersIterator.next();
            orders.setSurplusFee(0d);
            orders.setUpdateDt(new Date());
            orders.setStatus(OrderStatus.REFUND.toString());
            ordersService.edit(orders);
        }
    }

    /**
     * 统计用户可退款订单数量
     *
     * @param accountId
     * @return
     */
    @Override
    public Integer countBalanceOrder(Long accountId) {
        return ordersExService.countBalanceOrder(accountId);
    }

    /**
     * 统计用户进行中的订单
     *
     * @param accountId
     * @return
     */
    @Override
    public int countPendingOrder(Long accountId) {
        return ordersExService.countPendingOrder(accountId);
    }

    /**
     * 保存已退款订单
     *
     * @param refundOrderList
     */
    @Override
    public void saveRefundSuccessOrder(List<Orders> refundOrderList) {
        saveRefundOrder(refundOrderList,OrderStatus.REFUND);
    }

    /**
     * <pre>
     *  同步微信已经不能退款的退款单为已退款（本身是已经退过款但未同步的退款单）
     *  </pre>
     * @param failList 预退款失败的退款单
     */
    @Override
    public void syncRefund(List<Orders> failList) {
        Iterator<Orders> ordersIterator = failList.iterator();
        while (ordersIterator.hasNext()){
            Orders orders = ordersIterator.next();
            orders.setStatus(OrderStatus.REFUND.toString());
            orders.setUpdateDt(new Date());
            this.ordersService.edit(orders);
        }
    }

    /**
     * 添加订单为退款失败的单，等待定时任务处理
     *
     * @param refundFailList
     */
    @Override
    public void saveRefundFailOrder(List<Orders> refundFailList) {
        saveRefundOrder(refundFailList,OrderStatus.REFUND_FAIL);
    }

    /**
     * 修改所有的退款成功的订单为成功
     *
     * @param userOrderRefundSuccessDTOs
     */
    @Override
    public void editAllRefundSuccess(List<UserOrderRefundDTO> userOrderRefundSuccessDTOs) {
        Map<String,Object> params = new HashMap<>();
        params.put("status",OrderStatus.REFUND.toString());
        List<Long> ids = new ArrayList<>();
        userOrderRefundSuccessDTOs.stream().forEach((obj)->{
            ids.add(obj.getId());
            this.ordersService.editByWhereSql(params,"where parentOrderList like "+obj.getId()+"");
        });
        StringBuilder idsStr = new StringBuilder();
        ids.stream().forEach((id)->idsStr.append(id).append(","));
        //去掉尾部“，”
        if (idsStr.length()>0){
            idsStr.delete(idsStr.length()-1,idsStr.length());
        }
        this.ordersService.editByWhereSql(params,"where id in ("+idsStr+")");

    }

    /**
     * 退款订单的添加
     * @param refundList 退款单
     * @param orderStatus 退款状态
     *                    @see #saveRefundSuccessOrder(List) 保存退款成功的订单
     *                    @see #saveRefundFailOrder(List) 保存退款失败的订单
     */
    public void saveRefundOrder(List<Orders> refundList,OrderStatus orderStatus){
        Iterator<Orders> ordersIterator = refundList.iterator();
        Orders firstOrder = null;
        try {
            firstOrder = (Orders) BeanUtils.cloneBean(refundList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder orderIds = new StringBuilder();
        Double fee = 0D;
        while (ordersIterator.hasNext()) {
            Orders orders = ordersIterator.next();
            orderIds.append(orders.getParentOrder()).append(",");
            fee += orders.getTotalFee();
            ordersService.create(orders);
        }
        if (orderIds.length() > 0) {
            orderIds.delete(orderIds.length() - 1, orderIds.length());
        }
        if (firstOrder != null) {
            firstOrder.setType(OrderType.REFUND.toString());
            firstOrder.setTotalFee(fee);
            firstOrder.setParentOrderList(orderIds.toString());
            firstOrder.setStatus(orderStatus.toString());
        }
        ordersService.create(firstOrder);
    }
}
