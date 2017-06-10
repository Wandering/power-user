/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:43:45 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IAgenciesDAO;
import com.power.domain.Agencies;

public interface IAgenciesService extends IBaseService<Long, IAgenciesDAO, Agencies>,IPageService<IAgenciesDAO, Agencies> {

}
