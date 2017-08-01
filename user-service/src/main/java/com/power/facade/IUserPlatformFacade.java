/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:07 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.PlatformInfo;
import com.power.domain.UserPlatform;
import com.power.dto.UserPlatformDTO;

public interface IUserPlatformFacade extends IPersistenceProvider {
    UserPlatformDTO getWxPlatformByOpenId(String openId, Long agencyId);
    UserPlatform getWxPlatformByOpIdAndPid(String openId, Long platformId);
}
