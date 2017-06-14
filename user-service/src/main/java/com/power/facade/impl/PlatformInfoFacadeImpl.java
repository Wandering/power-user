/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:03 $ 
 */
        package com.power.facade.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.core.domain.OrderField;
import com.power.core.domain.SearchField;
import com.power.core.domain.Sorter;
import com.power.core.domain.SqlOrderEnum;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.exception.BizException;
import com.power.core.service.IBaseService;
import com.power.core.service.impl.AbstractPersistenceProvider;
import com.power.domain.*;
import com.power.facade.IPlatformInfoFacade;
import com.power.service.*;
import com.power.service.ex.IPlatformInfoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("PlatformInfoFacadeImpl")
public class PlatformInfoFacadeImpl extends AbstractPersistenceProvider implements IPlatformInfoFacade {
    @Autowired
    private IPlatformInfoService platformInfoService;
    @Autowired
    private IPlatformInfoExService platformInfoExService;
    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IUserPlatformService userPlatformService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserExpandService userExpandService;
//    @Transactional(propagation = Propagation.REQUIRED)
//    @Override
//    public void add() {
//        platformInfoService.add();
//    }

    @Override
    public IBaseService getMainService() {
        return platformInfoService;
    }

    @Override
    public PlatformInfo getPlatformInfoByAppId(String appId) {
        return null;
    }

    @Override
    public PlatformInfo getPlatformInfoByUniqueKey(String uniqueKey) {
        return platformInfoExService.getPlatformInfoByUniqueKey(uniqueKey);
    }

    @Override
    public List<PlatformInfo> getPlatformInfoByUserName(String userName) {
        return null;
    }

    @Override
    public List<PlatformInfo> getPlatformInfoByAgencyId(Long agencyId) {

        return null;
    }

    @Override
    public boolean wxSubscribe(Map<String,Object> wechat, String uniqueKey) {
        //查询公众号
        PlatformInfo platformInfo = getPlatformInfoByUniqueKey(uniqueKey);
        if (platformInfo == null){
            throw new BizException(ERRORCODE.PLATFORM_IS_NULL.getCode(),ERRORCODE.PLATFORM_IS_NULL.getMessage());
        }
        String openId = (String) wechat.get("openid");
        String unionId = (String) wechat.get("unionid");


        //查询公众号对应的用户
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("platformId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(platformInfo.getId().toString());
        condition.put("platformId",searchField);

        searchField = new SearchField();
        searchField.setField("unionId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(unionId);
        condition.put("unionId",searchField);

        searchField = new SearchField();
        searchField.setField("openId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(openId);
        condition.put("openId",searchField);

        UserPlatform userPlatform = userPlatformService.viewOne(null,condition,null);
        //如果存在 置状态为关注 如果不存在 进入添加用户流程 1关注  0 未关注
        if (userPlatform != null){
            userPlatform.setStatus(1);
            userPlatformService.edit(userPlatform);
        }else {
            condition = Maps.newHashMap();
            searchField = new SearchField();
            searchField.setField("platformId");
            searchField.setOp(SearchEnum.eq.getValue());
            searchField.setData(platformInfo.getId().toString());
            condition.put("platformId",searchField);

            searchField = new SearchField();
            searchField.setField("unionId");
            searchField.setOp(SearchEnum.eq.getValue());
            searchField.setData(unionId);
            condition.put("unionId",searchField);
            //判断是否是其他平台下用户  只取其中一个 目的为了取到用户ID
            userPlatform = userPlatformService.viewOne(null,condition,null);
            //假如不为空 说明在其他平台关注了  取到userId
            if (userPlatform !=null) {
                //清空ID和openId作为一个新对象保存
                userPlatform.setUserId(null);
                userPlatform.setOpenId(openId);
                userPlatform.setStatus(1);
                userPlatformService.create(userPlatform);
            }else {
                //为空说明整个系统中不存在这个用户 进入添加用户流程
                User user = new User();
                user.setAccountNo(UUID.randomUUID().toString());
                userService.create(user);
                Long userId = user.getId();
                //添加用户拓展信息
                UserExpand userExpand = new UserExpand();
                userExpand.setUserId(userId);
                userExpand.setSex(Integer.valueOf(wechat.get("sex").toString()));
                userExpand.setCity((String) wechat.get("city"));
                userExpand.setProvince(wechat.get("province").toString());
                userExpand.setCountry(wechat.get("country").toString());
                userExpand.setNickname(wechat.get("nickname").toString());
                userExpand.setHeadimgurl(wechat.get("headimgurl").toString());
                userExpand.setAgencyId(platformInfo.getAgencyId());
                userExpandService.create(userExpand);

                //添加用户公众号对应关系
                userPlatform = new UserPlatform();
                userPlatform.setOpenId(openId);
                userPlatform.setUnionId(unionId);
                userPlatform.setStatus(1);
                userPlatform.setPlatformId(platformInfo.getId());
                userPlatform.setUserId(userId);
                userPlatform.setCreateTime(System.currentTimeMillis()/1000);
                userPlatformService.create(userPlatform);

                //添加用户运营商对应关系
                UserAccount userAccount = new UserAccount();
                userAccount.setAgencyId(platformInfo.getAgencyId());
                userAccount.setUserId(userId);
                userAccountService.create(userAccount);
            }
        }

        return true;
    }

    @Override
    public boolean wxUnSubscribe(Map<String,Object> wechat, String uniqueKey) {
        //查询公众号
        PlatformInfo platformInfo = getPlatformInfoByUniqueKey(uniqueKey);
        if (platformInfo == null){
            throw new BizException("error","公众号/生活号不存在");
        }
        String openId = (String) wechat.get("openid");
        String unionId = (String) wechat.get("unionid");

        //查询公众号对应的用户
        Map<String,Object> condition = Maps.newHashMap();
        SearchField searchField = new SearchField();
        searchField.setField("platformId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(platformInfo.getId().toString());
        condition.put("platformId",searchField);

        searchField = new SearchField();
        searchField.setField("unionId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(unionId);
        condition.put("unionId",searchField);

        searchField = new SearchField();
        searchField.setField("openId");
        searchField.setOp(SearchEnum.eq.getValue());
        searchField.setData(openId);
        condition.put("openId",searchField);
        UserPlatform userPlatform = userPlatformService.viewOne(null,condition,null);
        userPlatform.setStatus(0);
        userPlatformService.edit(userPlatform);
        return true;
    }
}
