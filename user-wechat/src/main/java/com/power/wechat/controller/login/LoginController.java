package com.power.wechat.controller.login;

import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import com.power.facade.IUserAccountFacade;
import com.power.common.PlatformCache;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/9.
 */
@RestController
@RequestMapping("/wechat/login")
public class LoginController {

    @Autowired
    private IUserAccountFacade userAccountFacade;

    @Autowired
    private PlatformCache platformCache;

    @RequestMapping("/{uniqueKey}")
    @ResponseBody
   public Map<String,Object> wxLogin(@RequestParam("code")String code,@PathVariable("uniqueKey")String uniqueKey){
        String openId = "o9P_pv2gzYtOm6V_sDNhZ7HLWHyY";
//        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
//        String openId = null;
//        try {
//            //获取微信openId
//            WxMpOAuth2AccessToken wxMpOAuth2AccessToken  = wxMpService.oauth2getAccessToken(code);
//            openId = wxMpOAuth2AccessToken.getOpenId();
//        } catch (WxErrorException e) {
//            throw new BizException(ERRORCODE.CODE_BEEN_USED.getCode(),ERRORCODE.CODE_BEEN_USED.getMessage());
//        }
        Map<String,Object> rtnMap = null;
        rtnMap = userAccountFacade.login(openId,uniqueKey);
        rtnMap.put("openId",openId);
        return rtnMap;
   }

}
