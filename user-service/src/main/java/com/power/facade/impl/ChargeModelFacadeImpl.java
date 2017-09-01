/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:43 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.facade.IChargeModelFacade;
import com.power.service.IChargeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChargeModelFacadeImpl")
public class ChargeModelFacadeImpl extends AbstractPersistenceProvider implements IChargeModelFacade {
    @Autowired
    private IChargeModelService chargeModelService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        chargeModelService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return chargeModelService;
    }
}
