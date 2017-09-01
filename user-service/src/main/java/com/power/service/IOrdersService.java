/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:03 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IOrdersDAO;
import com.power.domain.Orders;

public interface IOrdersService extends IBaseService<Long, IOrdersDAO, Orders>,IPageService<IOrdersDAO, Orders> {

}
