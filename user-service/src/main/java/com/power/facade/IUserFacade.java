/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:05 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.dto.UserInfoDTO;

public interface IUserFacade extends IPersistenceProvider {

    UserInfoDTO getWxUserInfoByAccount(Long accountId);
}
