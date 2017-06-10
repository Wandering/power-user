/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:05 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserPlatformDAO;
import com.power.domain.UserPlatform;

public interface IUserPlatformService extends IBaseService<Long, IUserPlatformDAO, UserPlatform>,IPageService<IUserPlatformDAO, UserPlatform> {

}
