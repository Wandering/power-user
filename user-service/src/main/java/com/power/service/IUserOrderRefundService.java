/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:04 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserOrderRefundDAO;
import com.power.domain.UserOrderRefund;

public interface IUserOrderRefundService extends IBaseService<Long, IUserOrderRefundDAO, UserOrderRefund>,IPageService<IUserOrderRefundDAO, UserOrderRefund> {

}
