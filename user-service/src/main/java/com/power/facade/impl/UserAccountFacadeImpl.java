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
    private static final int TOKEN_TIME_OUT = 7;//7天
    private static final int USER_INFO_TIME_OUT = 8;//8天



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
            throw new BizException(ERRORCODE.USER_IS_NULL.getCode(),ERRORCODE.USER_IS_NULL.getMessage());
        }
        String loginKey  = USER_TOKEN+userAccount.getUserId()+"_"+userAccount.getAgencyId();

        String token = null;
        if (repository.exists(loginKey)){
            token = repository.get(loginKey);
        }else {
            String tokenKey = DESUtil.getEightByteMultypleStr(userAccount.getUserId(),userAccount.getAgencyId(),openId);
            try {
                token = UUID.nameUUIDFromBytes(tokenKey.getBytes()).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //token存放用户信息的key
            repository.set(loginKey, token);
            UserInfoDTO userInfoDTO;
            if (repository.exists(token)){
                userInfoDTO = JSON.parseObject(repository.get(token),UserInfoDTO.class);
            }else{
                UserExpand userExpand = userExpandService.view(userId);
                //注入用户信息
                userInfoDTO = new UserInfoDTO();
                BeanUtils.copyProperties(userExpand,userInfoDTO);
                userInfoDTO.setUserId(userId);
                userInfoDTO.setAccountId(userExpand.getUserId());
                User user = userService.view(userId);
                userInfoDTO.setPhone(user.getPhone());

                //重写线上数据
                repository.set(token,JSON.toJSONString(userInfoDTO));
                repository.expire(loginKey,TOKEN_TIME_OUT, TimeUnit.DAYS);
                //用户信息保持7天
                repository.expire(token,USER_INFO_TIME_OUT, TimeUnit.DAYS);
            }
            //放入当前内存中
            UserContext.setCurrentUser(userInfoDTO);
        }
        Map<String,Object> rtnMap = Maps.newHashMap();
        rtnMap.put("token",token);
        rtnMap.put("userId",userAccount.getAgencyId());
        return rtnMap;
    }
}
