/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:56 $ 
 */


package com.power.service.ex.impl;

import com.google.common.collect.Maps;
import com.power.core.domain.SearchField;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.dao.IUserAccountDAO;
import com.power.dao.IUserDAO;
import com.power.dao.ex.IUserAccountExDAO;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.service.ex.IUserAccountExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("UserAccountExServiceImpl")
public class UserAccountExServiceImpl implements IUserAccountExService {
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IUserAccountDAO userAccountDAO;
    @Autowired
    private IUserAccountExDAO userAccountExDAO;

    @Override
    public UserAccount queryUserByOpenId(String openId, String uniqueKey) {
        return userAccountExDAO.queryUserByOpenId(openId,uniqueKey);
    }
}
