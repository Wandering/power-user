/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:46 $ 
 */
        package com.power.facade.impl;


import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.UserAcounts;
import com.power.facade.IUserAcountsFacade;
import com.power.service.IUserAcountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("UserAcountsFacadeImpl")
public class UserAcountsFacadeImpl extends AbstractPersistenceProvider implements IUserAcountsFacade {
    @Autowired
    private IUserAcountsService userAcountsService;


//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        userAcountsService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return userAcountsService;
    }

    /**
     * 根据accountId查询用户的余额记录
     * @param accountId
     * @return
     */
    @Override
    public UserAcounts getUserAccountsByAccountId(Long accountId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",accountId);
        return this.userAcountsService.viewOne(map);
    }

    /**
     * <pre>
     *      退款时候锁定用户余额
     * </pre>
     *
     * @param userAcounts
     */
    @Override
    public void lockBalance(UserAcounts userAcounts) {
        userAcounts.setDeposit(userAcounts.getBalance());
        userAcounts.setBalance(0D);
        userAcountsService.edit(userAcounts);
    }
}
