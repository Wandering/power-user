/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-09-11 10:14:43 $ 
 */



package com.power.service;

import com.power.core.service.IBaseService;
import com.power.core.service.IPageService;
import com.power.dao.IUserRecommendDAO;
import com.power.domain.UserRecommend;

public interface IUserRecommendService extends IBaseService<Long, IUserRecommendDAO, UserRecommend>,IPageService<IUserRecommendDAO, UserRecommend> {

}
