/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:05 $ 
 */




package com.power.service.ex.impl;

import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.dao.IUserPlatformDAO;
import com.power.dao.ex.IOrdersExDAO;
import com.power.dao.ex.IUserPlatformExDAO;
import com.power.domain.OrderStatus;
import com.power.domain.OrderType;
import com.power.domain.UserPlatform;
import com.power.dto.UserPlatformDTO;
import com.power.service.ex.IOrdersExService;
import com.power.service.ex.IUserPlatformExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("OrdersExServiceImpl")
public class OrdersExServiceImpl implements IOrdersExService {

    @Autowired
    IOrdersExDAO ordersExDAO;
    /**
     * 统计用户充值订单数
     *
     * @param accountId
     * @return
     */
    @Override
    public Integer countBalanceOrder(Long accountId) {
        return ordersExDAO.countOrderByTypeAndStatus(accountId, OrderType.BALANCE.toString(), OrderStatus.PAYED.toString())
                + ordersExDAO.countOrderByTypeAndStatus(accountId, OrderType.BALANCE.toString(), OrderStatus.IN_COUNT.toString()) ;
    }

    /**
     * 统计用户借电订单数
     *
     * @param accountId
     * @return
     */
    @Override
    public int countPendingOrder(Long accountId) {
        return ordersExDAO.countOrderByTypeAndStatus(accountId, OrderType.CONSUME.toString(), OrderStatus.PENDING.toString()) +
                ordersExDAO.countOrderByTypeAndStatus(accountId, OrderType.CONSUME.toString(), OrderStatus.PRE_BORROW.toString());
    }
}
