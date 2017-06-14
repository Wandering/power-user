/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:05 $ 
 */



package com.power.service.ex;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserPlatformDAO;
import com.power.domain.UserPlatform;

public interface IUserPlatformExService{
    UserPlatform queryUserPlatformByPlatformId(Long platformId);
}
