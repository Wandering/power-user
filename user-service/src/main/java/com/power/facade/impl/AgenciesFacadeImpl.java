/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:43:45 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.facade.IAgenciesFacade;
import com.power.service.IAgenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AgenciesFacadeImpl")
public class AgenciesFacadeImpl extends AbstractPersistenceProvider implements IAgenciesFacade {
    @Autowired
    private IAgenciesService agenciesService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        iService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return agenciesService;
    }
}
