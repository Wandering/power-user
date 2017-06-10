/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:06 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.Agencies;
import com.power.facade.IUserAccountFacade;
import com.power.service.IAgenciesService;
import com.power.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserAccountFacadeImpl")
public class UserAccountFacadeImpl extends AbstractPersistenceProvider implements IUserAccountFacade {
    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IAgenciesService agenciesService;

//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userAccountService.add();
//    }

    @Override
    public IUserAccountService getMainService() {
        return userAccountService;
    }

    @Override
    public Agencies queryUserAgencyInfo(Long userId) {
        return null;
    }

}
