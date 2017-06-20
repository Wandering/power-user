package com.power.wechat.controller.user;

import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import com.power.domain.PlatformInfo;
import com.power.domain.User;
import com.power.dto.UserInfoDTO;
import com.power.facade.IPlatformInfoFacade;
import com.power.facade.IUserFacade;
import com.power.facade.IUserPlatformFacade;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/6/13.
 * 认证用户手机号码
 */
@RestController
@RequestMapping("/user/wechat/info")
public class UserController {
    @Autowired
    private IUserFacade userFacade;
    @Autowired
    private IUserPlatformFacade userPlatformFacade;
    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    @RequestMapping(value = "/queryUserByOpenId")
    @ResponseBody
    public PlatformInfo queryWxPlatform(@RequestParam("code") String code, @RequestParam("agencyId")Long agencyId){
        PlatformInfo platformInfo = platformInfoFacade.getWxPlatformInfoByAgencyId(agencyId);
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(platformInfo.getUniqueKey());
        String openId = null;
        try {
            //获取微信openId
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken  = wxMpService.oauth2getAccessToken(code);
            openId = wxMpOAuth2AccessToken.getOpenId();
        } catch (WxErrorException e) {
            throw new BizException(ERRORCODE.CODE_BEEN_USED.getCode(),ERRORCODE.CODE_BEEN_USED.getMessage());
        }
        return userPlatformFacade.getWxPlatformByOpenId(openId,agencyId);
    }

    @RequestMapping(value = "/queryOpenIdByAccountId")
    @ResponseBody
    public UserInfoDTO queryWxPlatform(@RequestParam("accountId") Long accountId){
        return userFacade.getWxUserInfoByAccount(accountId);
    }
}
