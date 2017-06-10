/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:56 $ 
 */



package com.power.service;


import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserAccountDAO;
import com.power.domain.UserAccount;

public interface IUserAccountService extends IBaseService<Long, IUserAccountDAO, UserAccount>,IPageService<IUserAccountDAO, UserAccount> {

}
