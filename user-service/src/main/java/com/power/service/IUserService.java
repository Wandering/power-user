/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:55 $ 
 */



package com.power.service;


import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserDAO;
import com.power.domain.User;

public interface IUserService extends IBaseService<Long, IUserDAO, User>,IPageService<IUserDAO, User> {

}
