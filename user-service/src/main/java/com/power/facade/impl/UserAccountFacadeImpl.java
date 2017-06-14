/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:06 $ 
 */
        package com.power.facade.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.common.PlatformCache;
import com.power.context.UserContext;
import com.power.core.cache.RedisRepository;
import com.power.core.exception.BizException;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.core.utils.DESUtil;
import com.power.domain.*;
import com.power.dto.UserInfoDTO;
import com.power.facade.IUserAccountFacade;
import com.power.service.IAgenciesService;
import com.power.service.IUserAccountService;
import com.power.service.IUserExpandService;
import com.power.service.IUserService;
import com.power.service.ex.IUserAccountExService;
import com.power.service.ex.IUserPlatformExService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
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
    @Autowired
    private IUserExpandService userExpandService;
    @Autowired
    private IUserPlatformExService userPlatformExService;
    @Autowired
    private RedisRepository<String,String> repository;
    @Autowired
    private PlatformCache platformCache;

    private static  final String USER_TOKEN = "user_token_";
    private static final String USER_INFO = "user_info_";
    private static final int TOKEN_TIME_OUT = 2;//2小时
    private static final int USER_INFO_TIME_OUT = 3;//3天



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
        Long userId = userAccount.getUserId();
        if (userAccount == null) {
            throw new BizException("error","非法用户");
        }
        String loginKey  = USER_TOKEN+userAccount.getUserId();
        String userInfoKey = USER_INFO+userId;

        String token = null;
        if (repository.exists(loginKey)){
            token = repository.get(loginKey);
        }else {
            String tokenKey = UUID.randomUUID().toString();
            try {
                token = DESUtil.encrypt(tokenKey,DESUtil.key);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //token存放用户信息的key
            repository.set(loginKey, token);
            repository.set(token,userInfoKey);
            UserInfoDTO userInfoDTO;
            if (repository.exists(userInfoKey)){
                userInfoDTO = JSON.parseObject(repository.get(userInfoKey),UserInfoDTO.class);
            }else{
                UserExpand userExpand = userExpandService.view(userId);
                //注入用户信息
                userInfoDTO = new UserInfoDTO();
                userInfoDTO.setUserId(userId);
                BeanUtils.copyProperties(userExpand,userInfoDTO);
            }
            if (userInfoDTO.getUserAccountMap().containsKey(uniqueKey)){
                userInfoDTO.getUserAccountMap().put(uniqueKey,userAccount);
                PlatformInfo platformInfo = platformCache.getCache(uniqueKey);
                UserPlatform userPlatform = userPlatformExService.queryUserPlatformByPlatformId(platformInfo.getId());
                userInfoDTO.getUserPlatformMap().put(uniqueKey,userPlatform);
                //重写线上数据
                repository.set(userInfoKey,JSON.toJSONString(userInfoDTO));
            };
            //放入当前内存中
            UserContext.setCurrentUser(userInfoDTO);
            //设置失效时间3天
            repository.expire(loginKey,TOKEN_TIME_OUT, TimeUnit.HOURS);
            //用户信息保持7天  如果有人登录 续期为3天
            repository.expire(userInfoKey,USER_INFO_TIME_OUT, TimeUnit.DAYS);
        }
        Map<String,Object> rtnMap = Maps.newHashMap();
        rtnMap.put("token",token);
        rtnMap.put("userId",userAccount.getAgencyId());
        return rtnMap;
    }
}
