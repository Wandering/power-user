/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:07 $ 
 */
        package com.power.facade.impl;


import com.power.common.PlatformEnum;
import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.PlatformInfo;
import com.power.dto.UserPlatformDTO;
import com.power.facade.IUserPlatformFacade;
import com.power.service.IUserPlatformService;
import com.power.service.ex.IUserPlatformExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserPlatformFacadeImpl")
public class UserPlatformFacadeImpl extends AbstractPersistenceProvider implements IUserPlatformFacade {
    @Autowired
    private IUserPlatformService userPlatformService;

    @Autowired
    private IUserPlatformExService userPlatformExService;
//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userPlatformService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return userPlatformService;
    }

    @Override
    public UserPlatformDTO getWxPlatformByOpenId(String openId, Long agencyId) {
        return userPlatformExService.queryPlatformByPlatformId(agencyId, PlatformEnum.WX.getCode(),openId);
    }
}
