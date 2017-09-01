/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:00 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.facade.IOrderLineFacade;
import com.power.service.IOrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("OrderLineFacadeImpl")
public class OrderLineFacadeImpl extends AbstractPersistenceProvider implements IOrderLineFacade {
    @Autowired
    private IOrderLineService orderLineService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        orderLineService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return orderLineService;
    }
}
