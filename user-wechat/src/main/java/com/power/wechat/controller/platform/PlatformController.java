package com.power.wechat.controller.platform;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.domain.PlatformInfo;
import com.power.domain.UserAccount;
import com.power.domain.UserPlatform;
import com.power.facade.IPlatformInfoFacade;
import com.power.facade.IUserAccountFacade;
import com.power.facade.IUserFacade;
import com.power.facade.IUserPlatformFacade;
import com.power.http.BizHttpClient;
import com.power.service.IUserExpandService;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7.
 */
@RestController
@RequestMapping("/platform/wechat")
public class PlatformController {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);


    @PostMapping("/{uniqueKey}/jsApi")
    public WxJsapiSignature syncWxUser(@PathVariable String uniqueKey,@RequestParam String url){
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        WxJsapiSignature wxJsapiSignature = null;
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        logger.debug("wxJsapiSignature",JSON.toJSONString(wxJsapiSignature));
        return wxJsapiSignature;
    }
}
