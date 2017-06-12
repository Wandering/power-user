/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:06 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.Agencies;
import com.power.domain.PlatformInfo;
import com.power.domain.UserAccount;

import java.util.Map;

public interface IUserAccountFacade extends IPersistenceProvider {
    Agencies queryUserAgencyInfo(Long userId);
    Map<String,Object> login(String openId,String uniqueKey);
}
