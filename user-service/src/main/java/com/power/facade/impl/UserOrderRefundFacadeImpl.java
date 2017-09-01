/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:04 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.Orders;
import com.power.domain.UserOrderRefund;
import com.power.dto.UserOrderRefundDTO;
import com.power.facade.IUserOrderRefundFacade;
import com.power.service.IUserOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("UserOrderRefundFacadeImpl")
public class UserOrderRefundFacadeImpl extends AbstractPersistenceProvider implements IUserOrderRefundFacade {
    @Autowired
    private IUserOrderRefundService userOrderRefundService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userOrderRefundService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return userOrderRefundService;
    }

    /**
     * 创建定时任务的订单
     *
     * @param refundFailList
     */
    @Override
    public void createTimerTask(List<Orders> refundFailList) {
        Iterator<Orders> ordersIterator =  refundFailList.iterator();
        while (ordersIterator.hasNext()){
            Orders orders = ordersIterator.next();
            UserOrderRefund userOrderRefund = new UserOrderRefund();
            userOrderRefund.setOrderId(orders.getId());
            long currTime = System.currentTimeMillis()/1000;
            userOrderRefund.setCreateDt(currTime);
            userOrderRefund.setUserId(orders.getOrderOwner());
            //这里可能会有重复添加，但该层不做处理，由上层来去掉重复防止重复调用
            this.getMainService().create(userOrderRefund);
        }
    }

    /**
     * 清理所有重新退款成功的订单
     *
     * @param userOrderRefundSuccessDTOs
     */
    @Override
    public void clear(List<UserOrderRefundDTO> userOrderRefundSuccessDTOs) {
        List<Long> ids = new ArrayList<>();
        userOrderRefundSuccessDTOs.stream().forEach((obj)-> ids.add(obj.getId()));
        this.getMainService().delete(ids);
    }

    /**
     * 查询所有用户为退款成功的订单
     *
     * @return
     */
    @Override
    public List<UserOrderRefundDTO> findAllUser() {
        return null;
    }
}
