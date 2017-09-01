/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:53 $ 
 */



package com.power.service.ex;


import com.power.domain.PlatformInfo;

public interface IOrdersExService {

    /**
     * 统计用户充值订单数
     * @param accountId
     * @return
     */
    Integer countBalanceOrder(Long accountId);


    /**
     * 统计用户借电订单数
     * @param accountId
     * @return
     */
    int countPendingOrder(Long accountId);
}
