package com.power.wechat.controller.user;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.domain.UserPlatform;
import com.power.facade.IPlatformInfoFacade;
import com.power.facade.IUserAccountFacade;
import com.power.facade.IUserFacade;
import com.power.facade.IUserPlatformFacade;
import com.power.http.BizHttpClient;
import com.power.service.IUserExpandService;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7.
 */
@RestController
@RequestMapping("/user/wechat/info/sync")
public class SyncWxUserController {

    private static final Logger logger = LoggerFactory.getLogger(SyncWxUserController.class);

    @Autowired
    private IUserPlatformFacade userPlatformFacade;

    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private IUserExpandService userExpandService;

    @Autowired
    private IUserAccountFacade userAccountFacade;


    @Autowired
    private BizHttpClient bizHttpClient;

    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    @GetMapping("/{uniqueKey}/syncWxUser")
    public void syncWxUser(@PathVariable String uniqueKey){
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        WxMpUserList wxMpUserList = null;
        try {
            wxMpUserList =  wxMpService.getUserService().userList(null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        logger.debug(JSON.toJSONString(wxMpUserList));

        Iterator<String> iterator =  wxMpUserList.getOpenids().iterator();
        int rows = wxMpUserList.getOpenids().size();
        logger.debug("--------------查询到{}条用户公众号--------------",rows);
        int count = 0;
        while (iterator.hasNext()){
            logger.debug("--------------当前开始处理第{}条用户信息,共{}条--------------",count,rows);
            String openId = iterator.next();
            WxMpUser wxMpUser = null;
            try {
                wxMpUser = wxMpService.getUserService().userInfo(openId);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
            //微信远程查询账号信息
            Map<String,Object> wechat = Maps.newHashMap();
            {
                wechat.put("openid",wxMpUser.getOpenId());
                wechat.put("unionid",wxMpUser.getUnionId());
                wechat.put("sex",wxMpUser.getSexId());
                wechat.put("city",wxMpUser.getCity());
                wechat.put("province",wxMpUser.getProvince());
                wechat.put("country",wxMpUser.getCountry());
                wechat.put("nickname",wxMpUser.getNickname());
                wechat.put("headimgurl",wxMpUser.getHeadImgUrl());
            }

            Map<String,Object> map = Maps.newHashMap();
            map.put("openId",wxMpUser.getOpenId());
            map.put("unionId",wxMpUser.getUnionId());
            UserPlatform userPlatform = (UserPlatform) userPlatformFacade.getMainService().viewOne(map);
            if (userPlatform == null){
                logger.debug("--------------当前用户未注册---------------");
                platformInfoFacade.wxSubscribe(wechat,uniqueKey);
            }else {
                map = new HashMap<>();
                map.put("userId",userPlatform.getUserId());
                UserAccount userAccount = (UserAccount) userAccountFacade.getMainService().viewOne(map);
                try {
                    bizHttpClient.syncRegUserToBiz(userAccount.getId());
                }catch (Exception e){}
            }
            logger.debug("--------------结束处理第{}条用户信息,共{}条---------------",count++,rows);
        }

    }
}
