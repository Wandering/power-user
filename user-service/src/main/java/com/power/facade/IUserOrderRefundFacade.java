/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:04 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.Orders;
import com.power.dto.UserOrderRefundDTO;

import java.util.List;

public interface IUserOrderRefundFacade extends IPersistenceProvider {

    /**
     * 创建定时任务的订单
     * @param refundFailList
     */
    void createTimerTask(List<Orders> refundFailList);

    /**
     * 清理所有重新退款成功的订单
     * @param userOrderRefundSuccessDTOs
     */
    void clear(List<UserOrderRefundDTO> userOrderRefundSuccessDTOs);

    /**
     * 查询所有用户为退款成功的订单
     * @return
     */
    List<UserOrderRefundDTO> findAllUser();
}
