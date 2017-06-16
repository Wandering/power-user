/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:05 $ 
 */
        package com.power.facade.impl;


import com.power.common.PlatformEnum;
import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.dto.UserInfoDTO;
import com.power.facade.IUserFacade;
import com.power.service.IUserService;
import com.power.service.ex.IUserExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserFacadeImpl")
public class UserFacadeImpl extends AbstractPersistenceProvider implements IUserFacade {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserExService userExService;
//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return userService;
    }

    @Override
    public UserInfoDTO getWxUserInfoByAccount(Long accountId) {
        return userExService.getUserInfoByAccount(accountId, PlatformEnum.WX.getCode());
    }
}
