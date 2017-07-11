package com.power.test.web;

import com.alibaba.fastjson.JSON;
import com.power.core.cache.RedisRepository;
import com.power.core.protocol.ResponseT;
import com.power.domain.ERRORCODE;
import com.power.dto.UserInfoDTO;
import com.power.test.BaseTest;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by Administrator on 2017/6/13.
 */

public class UserInfoTest extends BaseTest{

    Logger logger = Logger.getLogger(UserInfoTest.class);
    private final static String USER_SMS = "USER_SMS_";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RedisRepository<String,String> redis;


    private static final String phone = "17602903609";
    private static final String checkKey = USER_SMS+phone;
    private static final String openId = "oqoGE0UJGJSuFrSbJX-SwcpVhLYY";
    private static final String agencyId = "3";
    private static final String accountId = "1";
    private static final String userId = "1";
    private static final String ok_rtn = "0000000";
    private static final String uniqueKey = "powertest";

    /**
     * 登录
     * @throws Exception
     */
    @Test
    public void testWxOpenLogin() throws Exception {
//        given(WxMpServiceUtil.getWxMpService("ppower")).willReturn(new WxMpServiceUtil());
//        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
//        wxMpOAuth2AccessToken.setOpenId("o9P_pv2gzYtOm6V_sDNhZ7HLWHyY");
//        given(wxMpService.oauth2getAccessToken("123")).willReturn(wxMpOAuth2AccessToken);
        login(openId,agencyId,accountId);
    }

    @Test
    public void testSMS() throws Exception {
//        given(WxMpServiceUtil.getWxMpService("ppower")).willReturn(new WxMpServiceUtil());
//        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
//        wxMpOAuth2AccessToken.setOpenId("o9P_pv2gzYtOm6V_sDNhZ7HLWHyY");
//        given(wxMpService.oauth2getAccessToken("123")).willReturn(wxMpOAuth2AccessToken);

        String token = login(openId,agencyId,accountId);
        this.mvc.perform(get("/user/wechat/auth/{uniqueKey}/captcha/sendSms",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("phone",phone)
                .param("token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andExpect(jsonPath("$.bizData").value("true"))
        ;
        String checkCode = redis.get(checkKey);
        this.mvc.perform(get("/user/wechat/auth/{uniqueKey}/captcha/checkSms",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("phone",phone)
                .param("checkCode",checkCode)
                .param("token", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ERRORCODE.TOKEN_INVALID_OR_NOTHINGNESS.getCode()));
        this.mvc.perform(get("/user/wechat/auth/{uniqueKey}/captcha/checkSms",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("phone",phone)
                .param("checkCode","123")
                .param("token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ERRORCODE.SMS_CHECK_ERROR.getCode()));
        this.mvc.perform(get("/user/wechat/auth/{uniqueKey}/captcha/checkSms",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("phone",phone)
                .param("checkCode",checkCode)
                .param("token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andExpect(jsonPath("$.bizData").value("true"));
        Assert.isTrue(!redis.exists(checkKey),"验证码未清除");
        UserInfoDTO userInfoDTO = JSON.parseObject(redis.get(token),UserInfoDTO.class);
        Assert.isTrue(userInfoDTO!=null,"用户对象为空");
        Assert.isTrue(userInfoDTO.getAccountId().equals(Long.valueOf(accountId)),"accountId错误");
        Assert.isTrue(userInfoDTO.getPhone()!=null,"用户手机号码为空");
        Assert.isTrue(userInfoDTO.getPhone().equals(phone),"用户手机号码错误");
        Assert.isTrue(!userInfoDTO.getHeadimgurl().isEmpty(),"用户头像空");
        Assert.isTrue(!userInfoDTO.getNickname().isEmpty(),"用户昵称为空");
        Assert.isTrue(userInfoDTO.getOpenId().equals(openId),"openId错误");

    }

    @Test
    public void testQueryWxPlatform() throws Exception {

        String token = login(openId,agencyId,accountId);
        this.mvc.perform(get("/user/wechat/info/queryOpenIdByAccountId").accept(MediaType.APPLICATION_JSON_UTF8)
                .param("accountId",accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andExpect(jsonPath("$.bizData").isNotEmpty())
                .andExpect(jsonPath("$.bizData.userId").value(userId))
                .andExpect(jsonPath("$.bizData.openId").value(openId))
//                .andExpect(jsonPath("$.bizData.phone").value("17602903609"))
                .andExpect(jsonPath("$.bizData.accountId").value(accountId))
                .andExpect(jsonPath("$.bizData.headimgurl").isNotEmpty())
                .andExpect(jsonPath("$.bizData.nickname").isNotEmpty())
                .andExpect(jsonPath("$.bizData.phone").value(phone))
        ;

    }

    private String login(String openId,String agencyId,String accountId) throws Exception {
        String rtn = this.mvc.perform(get("/user/wechat/login/{uniqueKey}/open",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("openId",openId)
                .param("agencyId",agencyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andExpect(jsonPath("$.bizData").isNotEmpty())
                .andExpect(jsonPath("$.bizData.userId").value(accountId))
                .andExpect(jsonPath("$.bizData.token").isNotEmpty()).andReturn().getResponse().getContentAsString();
        ResponseT responseT = JSON.parseObject(rtn, ResponseT.class);
        String token = ((Map<String,Object>)responseT.getBizData()).get("token").toString();
        Assert.isTrue(token!=null,"token为空");
        UserInfoDTO userInfoDTO = JSON.parseObject(redis.get(token),UserInfoDTO.class);
        Assert.isTrue(userInfoDTO!=null,"用户对象为空");
        Assert.isTrue(userInfoDTO.getAccountId().equals(Long.valueOf(accountId)),"accountId错误");
//        Assert.isTrue(userInfoDTO.getPhone()!=null,"用户手机号码为空");
//        Assert.isTrue(userInfoDTO.getPhone().equals(phone),"用户手机号码错误");
        Assert.isTrue(!userInfoDTO.getHeadimgurl().isEmpty(),"用户头像空");
        Assert.isTrue(!userInfoDTO.getNickname().isEmpty(),"用户昵称为空");
        Assert.isTrue(userInfoDTO.getOpenId().equals(openId),"openId错误");
        return token;
    }


    @Test
    public void testCallback() throws Exception {

        String msg = "<xml>\n" +
                "<ToUserName><![CDATA[123]]></ToUserName>\n" +
                "<FromUserName><![CDATA[oqoGE0UJGJSuFrSbJX-SwcpVhLYY]]></FromUserName>\n" +
                "<CreateTime>123456789</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[subscribe]]></Event>\n" +
                "</xml>";

        String uniqueKey = "powertest";
        String timestamp = "123344";
        String nonce = "123344";
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        String signature = SHA1.gen(wxMpService.getWxMpConfigStorage().getToken(), timestamp, nonce);
        this.mvc.perform(post("/wechat/callback/{uniqueKey}/callback",uniqueKey)
                .param("nonce", nonce)
                .param("timestamp",timestamp)
                .param("signature",signature)
                .content(msg)
        ).andExpect(status().isOk());
    }
}
