/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:46 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.UserAcounts;

public interface IUserAcountsFacade extends IPersistenceProvider {

    /**
     * 根据accountId查询用户的余额记录
     * @param accountId
     * @return
     */
    UserAcounts getUserAccountsByAccountId(Long accountId);

    /**
     * <pre>
     *      退款时候锁定用户余额
     * </pre>
     * @param userAcounts
     */
    void lockBalance(UserAcounts userAcounts);
}
