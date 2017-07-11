package com.power.test.wechat;

import com.alibaba.fastjson.JSON;
import com.power.core.protocol.ResponseT;
import com.power.domain.ERRORCODE;
import com.power.test.BaseTest;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2017/7/11.
 */
public class TestWechat extends BaseTest{

    String uniqueKey = "powertest";
    String url = "/platform/wechat";
    @Autowired
    private MockMvc mvc;

    private static final String ok_rtn = "0000000";

    @Test
    public void testWechat() throws Exception {
        String json = this.mvc.perform(post("/platform/wechat/{uniqueKey}/jsApi?url={url}",uniqueKey,url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andReturn().getResponse().getContentAsString();

        WxJsapiSignature wxJsapiSignature = JSON.parseObject(JSON.parseObject(json, ResponseT.class).getBizData().toString(),WxJsapiSignature.class);
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        Assert.isTrue(wxJsapiSignature.getSignature().equals(SHA1.genWithAmple("jsapi_ticket=" + wxMpService.getJsapiTicket(false),
                "noncestr=" + wxJsapiSignature.getNonceStr(), "timestamp=" + wxJsapiSignature.getTimestamp(), "url=" + url)),"微信jsAPI校验错误");
    }

    /**
     * 测试获取JSAPI权限
     * @throws Exception
     */
    @Test
    public void testJsApiWechat() throws Exception {
        String json = this.mvc.perform(post("/platform/wechat/{uniqueKey}/jsApi?url={url}",uniqueKey,url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andReturn().getResponse().getContentAsString();

        WxJsapiSignature wxJsapiSignature = JSON.parseObject(JSON.parseObject(json, ResponseT.class).getBizData().toString(),WxJsapiSignature.class);
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        Assert.isTrue(wxJsapiSignature.getSignature().equals(SHA1.genWithAmple("jsapi_ticket=" + wxMpService.getJsapiTicket(false),
                "noncestr=" + wxJsapiSignature.getNonceStr(), "timestamp=" + wxJsapiSignature.getTimestamp(), "url=" + url)),"微信jsAPI校验错误");
    }
}
