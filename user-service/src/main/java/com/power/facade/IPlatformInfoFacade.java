/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:08:03 $ 
 */
package com.power.facade;


import com.power.core.service.IPersistenceProvider;
import com.power.domain.PlatformInfo;

import java.util.List;
import java.util.Map;

public interface IPlatformInfoFacade extends IPersistenceProvider {
    /**
     * 根据appId获取公众号
     * @param appId
     * @return
     */
    PlatformInfo getPlatformInfoByAppId(String appId);

    /**
     * 根据全局唯一公众号key 获取公众号
     * @param uniqueKey
     * @return
     */
    PlatformInfo getPlatformInfoByUniqueKey(String uniqueKey);


    /**
     * 根据微信号获取公众号列表
     * @param userName
     * @return
     */
    List<PlatformInfo> getPlatformInfoByUserName(String userName);

    /**
     * 根据运营商获取公众号列表 一个运营商只有一个公众号
     * @param agencyId
     * @return
     */
    PlatformInfo getWxPlatformInfoByAgencyId(Long agencyId);

    /**
     * 用户关注公众号
     * @param fromUserName
     * @param uniqueKey
     * @return
     */
    String wxSubscribe(Map<String,Object> fromUserName, String uniqueKey);

    /**
     * 用户取消关注公众号
     * @param wechat
     * @param uniqueKey
     * @return
     */
    boolean wxUnSubscribe(Map<String,Object> wechat, String uniqueKey);
}
