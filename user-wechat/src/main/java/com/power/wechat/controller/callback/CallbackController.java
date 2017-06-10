package com.power.wechat.controller.callback;

import com.google.common.collect.Maps;
import com.power.domain.PlatformInfo;
import com.power.facade.IPlatformInfoFacade;
import com.power.wechat.util.PlatformCache;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/9.
 */
@RestController
@RequestMapping("/wechat")
public class CallbackController {

    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    @Autowired
    private PlatformCache platformCache;


    @Autowired
    private WxMpService wxMpService;
    /**
     * @param adminUser 开发者微信号
     * @param openId 发送方帐号（一个OpenID）
     * @param openId 消息创建时间 （整型）
     * @param MsgType 消息类型，event
     * @param Event 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    @RequestMapping("/callback/{uniqueKey}/")
    @ResponseBody
    public void followCallback(@RequestParam("ToUserName") String adminUser, @RequestParam("FromUserName") String openId, String CreateTime, String MsgType, String Event, String uniqueKey){
        PlatformInfo platformInfo = platformCache.getCache(uniqueKey);
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        //wx0a6f912b64eaf720
        config.setAppId(platformInfo.getAppId()); // 设置微信公众号的appid
        //2303a0a04558dfa2db0ce56087843f45
        config.setSecret(platformInfo.getSecret()); // 设置微信公众号的app corpSecret
        //
        config.setToken(platformInfo.getToken()); // 设置微信公众号的token
        //
        config.setAesKey(platformInfo.getAesKey()); // 设置微信公众号的EncodingAESKey
        WxMpUser wxMpUser = null;
        try {
//            String wxAccessToken = wxMpService.getAccessToken();
            wxMpUser = wxMpService.getUserService().userInfo(openId);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        //微信远程查询账号信息
        Map<String,Object> wechat = Maps.newHashMap();
        {
            wechat.put("openid",wxMpUser.getOpenId());
            wechat.put("unionid",wxMpUser.getUnionId());
            wechat.put("sex",wxMpUser.getSex());
            wechat.put("city",wxMpUser.getCity());
            wechat.put("province",wxMpUser.getProvince());
            wechat.put("country",wxMpUser.getCountry());
            wechat.put("nickname",wxMpUser.getNickname());
            wechat.put("headimgurl",wxMpUser.getHeadImgUrl());
        }

        boolean flag = false;
        //判定关注类型
        switch (Event){
            case "subscribe":
                //关注
                flag =  platformInfoFacade.wxSubscribe(wechat,uniqueKey);
                break;
            case "unsubscribe":
                //取消关注
                flag = platformInfoFacade.wxUnSubscribe(wechat,uniqueKey);
                break;
            default:
                break;
        }
    }

}
