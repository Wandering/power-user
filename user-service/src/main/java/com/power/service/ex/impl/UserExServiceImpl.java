/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:55 $ 
 */




package com.power.service.ex.impl;

import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.service.impl.AbstractPageService;
import com.power.dao.IUserDAO;
import com.power.dao.ex.IUserExDAO;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.dto.UserInfoDTO;
import com.power.service.IUserService;
import com.power.service.ex.IUserExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("UserExServiceImpl")
public class UserExServiceImpl implements IUserExService{
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IUserExDAO userExDAO;

    @Override
    public UserInfoDTO getUserInfoByAccount(Long accountId,Integer type) {
        return userExDAO.getUserInfoByAccount(accountId,type);
    }
}
