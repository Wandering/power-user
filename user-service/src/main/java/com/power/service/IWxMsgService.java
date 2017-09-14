/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-09-11 10:14:44 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IWxMsgDAO;
import com.power.domain.WxMsg;

public interface IWxMsgService extends IBaseService<Integer, IWxMsgDAO, WxMsg>,IPageService<IWxMsgDAO, WxMsg> {

}
