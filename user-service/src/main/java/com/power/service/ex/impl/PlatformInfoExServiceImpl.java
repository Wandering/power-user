/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:53 $ 
 */



package com.power.service.ex.impl;

import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.dao.IPlatformInfoDAO;
import com.power.domain.PlatformInfo;
import com.power.service.ex.IPlatformInfoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlatformInfoExServiceImpl implements IPlatformInfoExService{

    @Autowired
    IPlatformInfoDAO platformInfoDAO;

    @Override
    public PlatformInfo getPlatformInfoByUniqueKey(String uniqueKey) {
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("uniqueKey");
        searchField.setData(uniqueKey);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("uniqueKey",searchField);
        return platformInfoDAO.queryOne(null,condition,null);
    }

    @Override
    public PlatformInfo getPlatformInfoByAgencyAndType(Long agencyId, Integer type) {
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("agencyId");
        searchField.setData(agencyId);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("agencyId",searchField);
        searchField = new SearchField();
        searchField.setField("type");
        searchField.setData(type);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("type",searchField);
        return platformInfoDAO.queryOne(null,condition,null);
    }
}
