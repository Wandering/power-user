/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:06 $ 
 */
package com.power.facade.impl;


import com.google.common.collect.Maps;
import com.power.common.UserRedisCache;
import com.power.context.UserContext;
import com.power.core.cache.RedisRepository;
import com.power.core.exception.BizException;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.core.utils.DESUtil;
import com.power.domain.*;
import com.power.dto.UserInfoDTO;
import com.power.facade.IUserAccountFacade;
import com.power.service.IUserAccountService;
import com.power.service.IUserService;
import com.power.service.ex.IUserAccountExService;
import com.power.service.ex.IUserExpandExService;
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
    private IUserService userService;
    @Autowired
    private RedisRepository<String,String> repository;

    @Autowired
    private IUserExpandExService userExpandExService;

    @Autowired
    private UserRedisCache userRedisCache;
    private static  final String USER_TOKEN = "user_token_";
    private static final String USER_INFO = "user_info_";
    private static final int TOKEN_TIME_OUT = 7;//7天




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
            throw new BizException(ERRORCODE.USER_IS_NULL.getCode(),ERRORCODE.USER_IS_NULL.getMessage());
        }
        Long userId = userAccount.getUserId();

        String token = null;
        String tokenKey = DESUtil.getEightByteMultypleStr(userAccount.getUserId(), userAccount.getAgencyId(), openId);
        try {
            token = UUID.nameUUIDFromBytes(tokenKey.getBytes()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserInfoDTO userInfoDTO;
        UserExpand userExpand = userExpandExService.queryByUserIdAndAgencyId(userId, userAccount.getAgencyId());
        //注入用户信息
        userInfoDTO = new UserInfoDTO();
        userInfoDTO.setCity(userExpand.getCity());
        userInfoDTO.setCountry(userExpand.getCountry());
        userInfoDTO.setHeadimgurl(userExpand.getHeadimgurl());
        userInfoDTO.setProvince(userExpand.getProvince());
        userInfoDTO.setNickname(userExpand.getNickname());
        userInfoDTO.setAgencyId(userExpand.getAgencyId());
        userInfoDTO.setSex(userExpand.getSex());
        userInfoDTO.setUserId(userId);
        userInfoDTO.setAccountId(userAccount.getId());
        User user = userService.view(userId);
        userInfoDTO.setPhone(user.getPhone());
        userInfoDTO.setOpenId(openId);
        userInfoDTO.setUniqueKey(uniqueKey);
        //重写线上数据
        userRedisCache.putUserInfoDto(token, userInfoDTO);

        //放入当前内存中
        UserContext.setCurrentUser(userInfoDTO);
        Map<String,Object> rtnMap = Maps.newHashMap();
        rtnMap.put("token",token);
        //业务ID
        rtnMap.put("userId",userAccount.getId());
        return rtnMap;
    }
}
