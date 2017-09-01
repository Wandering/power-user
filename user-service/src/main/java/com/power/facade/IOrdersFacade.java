/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:03 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.Orders;
import com.power.dto.UserOrderRefundDTO;

import java.util.List;

public interface IOrdersFacade extends IPersistenceProvider {

    /**
     * 查询用户未清算订单
     * @param accountId
     * @return
     */
    List<Orders> findAllBalanceOrder(Long accountId);

    /**
     * 退款订单
     * @param refundSuccessList
     */
    void refundSuccess(List<Orders> refundSuccessList);

    /**
     * 统计用户可退款订单数量
     * @param accountId
     * @return
     */
    Integer countBalanceOrder(Long accountId);

    /**
     * 统计用户进行中的订单
     * @param accountId
     * @return
     */
    int countPendingOrder(Long accountId);

    /**
     * 保存已退款订单
     * @param refundOrderList
     */
    void saveRefundSuccessOrder(List<Orders> refundOrderList);

    /**
     * 处理微信退款单为
     * @param failList
     */
    void syncRefund(List<Orders> failList);

    /**
     * 添加订单为退款失败的单，等待定时任务处理
     * @param refundFailList
     */
    void saveRefundFailOrder(List<Orders> refundFailList);

    /**
     * 修改所有的退款成功的订单为成功
     * @param userOrderRefundSuccessDTOs
     */
    void editAllRefundSuccess(List<UserOrderRefundDTO> userOrderRefundSuccessDTOs);
}
