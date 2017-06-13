/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:06 $ 
 */
        package com.power.facade.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.core.cache.RedisRepository;
import com.power.core.exception.BizException;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.core.utils.DESUtil;
import com.power.domain.Agencies;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.facade.IUserAccountFacade;
import com.power.service.IAgenciesService;
import com.power.service.IUserAccountService;
import com.power.service.IUserService;
import com.power.service.ex.IUserAccountExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("UserAccountFacadeImpl")
public class UserAccountFacadeImpl extends AbstractPersistenceProvider implements IUserAccountFacade {
    @Autowired
    private IUserAccountExService userAccountExService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IAgenciesService agenciesService;

    @Autowired
    private IUserService userService;

    private static  final String USER_TOKEN = "user_token_";

    @Autowired
    private RedisRepository<String,String> repository;

    private static final String USER_INFO = "user_info_";

    @Override
    public IUserAccountService getMainService() {
        return userAccountService;
    }

    @Override
    public Agencies queryUserAgencyInfo(Long userId) {
        return null;
    }


    @Override
    public Map<String,Object> login(String openId, String uniqueKey) {
        UserAccount userAccount = userAccountExService.queryUserByOpenId(openId,uniqueKey);
        if (userAccount == null) {
            throw new BizException("error","非法用户");
        }
        String loginKey  = USER_TOKEN+userAccount.getUserId();
        String token = null;
        if (repository.exists(loginKey)){
            token = repository.get(loginKey);
        }else {
            String tokenKey = DESUtil.getEightByteMultypleStr(userAccount.getUserId(),userAccount.getAgencyId(),openId);
            try {
                token = DESUtil.encrypt(tokenKey,DESUtil.key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            repository.set(loginKey, token);
            repository.set(token, JSON.toJSONString(userAccount));
            repository.expire(loginKey,2, TimeUnit.HOURS);
            repository.expire(USER_INFO+token,2, TimeUnit.HOURS);

        }
        Map<String,Object> rtnMap = Maps.newHashMap();
        rtnMap.put("token",token);
        rtnMap.put("userId",userAccount.getAgencyId());
        return rtnMap;
    }
}
