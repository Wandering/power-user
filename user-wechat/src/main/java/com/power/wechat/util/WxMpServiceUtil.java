package com.power.wechat.util;

import com.google.common.collect.Maps;
import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import com.power.domain.PlatformInfo;
import com.power.facade.IPlatformInfoFacade;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
@Component
public class WxMpServiceUtil {
    private static IPlatformInfoFacade platformInfoFacade;
    @Autowired
    private IPlatformInfoFacade platformInfoAutoFacade;
    private static Map<String,WxMpService> map = Maps.newConcurrentMap();
    @PostConstruct
    public void init(){
        WxMpServiceUtil.platformInfoFacade=platformInfoAutoFacade;
        List<PlatformInfo> platformInfos =  platformInfoFacade.getMainService().getDao().findAll();
        for (PlatformInfo platformInfo : platformInfos){
            if (platformInfo.getType() != 1)continue;
            WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
            //wx0a6f912b64eaf720
            config.setAppId(platformInfo.getAppId()); // 设置微信公众号的appid
            //2303a0a04558dfa2db0ce56087843f45
            config.setSecret(platformInfo.getSecret()); // 设置微信公众号的app corpSecret
            //
            config.setToken(platformInfo.getToken()); // 设置微信公众号的token
            //
            config.setAesKey(platformInfo.getAesKey()); // 设置微信公众号的EncodingAESKey
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(config);
            map.put(platformInfo.getUniqueKey(),wxMpService);
        }
    }

    public static WxMpService getWxMpService(String uniqueKey){
        //检测新加入的服务号
        if (!map.containsKey(uniqueKey)){
            PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(uniqueKey);
            if (platformInfo==null){
                throw new BizException(ERRORCODE.PLATFORM_IS_NULL.getCode(),ERRORCODE.PLATFORM_IS_NULL.getMessage());
            }
            WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
            //wx0a6f912b64eaf720
            config.setAppId(platformInfo.getAppId()); // 设置微信公众号的appid
            //2303a0a04558dfa2db0ce56087843f45
            config.setSecret(platformInfo.getSecret()); // 设置微信公众号的app corpSecret
            //
            config.setToken(platformInfo.getToken()); // 设置微信公众号的token
            //
            config.setAesKey(platformInfo.getAesKey()); // 设置微信公众号的EncodingAESKey
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(config);
            map.put(platformInfo.getUniqueKey(),wxMpService);
        }
        return map.get(uniqueKey);
    }
}
