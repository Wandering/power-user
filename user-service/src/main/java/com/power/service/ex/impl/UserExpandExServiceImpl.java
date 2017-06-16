/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 22:36:04 $ 
 */




package com.power.service.ex.impl;


import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.service.impl.AbstractPageService;
import com.power.dao.IUserExpandDAO;
import com.power.domain.UserExpand;
import com.power.service.IUserExpandService;
import com.power.service.ex.IUserExpandExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("UserExpandExServiceImpl")
public class UserExpandExServiceImpl implements IUserExpandExService {
    @Autowired
    private IUserExpandDAO userExpandDAO;



    public UserExpand queryByUserIdAndAgencyId(Long userId,Long agencyId){
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("agencyId");
        searchField.setData(agencyId);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("agencyId",searchField);
        searchField = new SearchField();
        searchField.setField("userId");
        searchField.setData(userId);
        searchField.setOp(SearchEnum.eq.getValue());
        condition.put("userId",searchField);
        return userExpandDAO.queryOne(null,condition,null);
    }

//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<UserExpand> findAll() {
//        return userExpandDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected UserExpandDAO getDao() {
//        return userExpandDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}
