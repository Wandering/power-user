/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:05 $ 
 */




package com.power.service.ex.impl;

import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.service.impl.AbstractPageService;
import com.power.dao.IUserPlatformDAO;
import com.power.dao.ex.IUserPlatformExDAO;
import com.power.domain.PlatformInfo;
import com.power.domain.UserPlatform;
import com.power.dto.UserPlatformDTO;
import com.power.service.IUserPlatformService;
import com.power.service.ex.IUserPlatformExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("UserPlatformExServiceImpl")
public class UserPlatformExServiceImpl implements IUserPlatformExService {
    @Autowired
    private IUserPlatformDAO userPlatformDAO;
    @Autowired
    private IUserPlatformExDAO userPlatformExDAO;

    @Override
    public UserPlatform queryUserPlatformByPlatformId(Long platformId) {
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("platformId");
        searchField.setData(platformId);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("platformId",searchField);
        return userPlatformDAO.queryOne(null,condition,null);
    }

    @Override
    public UserPlatformDTO queryPlatformByPlatformId(Long agencyId, Integer type, String openId) {

        return userPlatformExDAO.getPlatformByOpenIdAndType(agencyId,type,openId);
    }
}
