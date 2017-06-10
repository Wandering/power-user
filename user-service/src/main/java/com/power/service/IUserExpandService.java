/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:04 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserExpandDAO;
import com.power.domain.UserExpand;

public interface IUserExpandService extends IBaseService<Long, IUserExpandDAO, UserExpand>,IPageService<IUserExpandDAO, UserExpand> {

}
