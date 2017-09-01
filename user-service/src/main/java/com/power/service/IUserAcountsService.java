/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:46 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserAcountsDAO;
import com.power.domain.UserAcounts;

public interface IUserAcountsService extends IBaseService<Long, IUserAcountsDAO, UserAcounts>,IPageService<IUserAcountsDAO, UserAcounts> {

}
