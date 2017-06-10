/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:04 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.facade.IUserExpandFacade;
import com.power.service.IUserExpandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserExpandFacadeImpl")
public class UserExpandFacadeImpl extends AbstractPersistenceProvider implements IUserExpandFacade {
    @Autowired
    private IUserExpandService userExpandService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userExpandService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return userExpandService;
    }
}
