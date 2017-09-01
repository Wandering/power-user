/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:00 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IOrderLineDAO;
import com.power.domain.OrderLine;

public interface IOrderLineService extends IBaseService<Long, IOrderLineDAO, OrderLine>,IPageService<IOrderLineDAO, OrderLine> {

}
