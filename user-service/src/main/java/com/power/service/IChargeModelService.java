/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:43 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IChargeModelDAO;
import com.power.domain.ChargeModel;

public interface IChargeModelService extends IBaseService<Long, IChargeModelDAO, ChargeModel>,IPageService<IChargeModelDAO, ChargeModel> {

}
