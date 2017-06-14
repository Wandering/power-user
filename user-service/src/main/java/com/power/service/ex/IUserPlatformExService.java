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
import com.power.domain.PlatformInfo;
import com.power.domain.UserPlatform;
import com.power.dto.UserPlatformDTO;

public interface IUserPlatformExService{
    UserPlatform queryUserPlatformByPlatformId(Long platformId);
    UserPlatformDTO queryPlatformByPlatformId(Long agencyId, Integer type, String openId);
}
