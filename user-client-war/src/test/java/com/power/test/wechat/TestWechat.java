package com.power.test.wechat;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.core.protocol.ResponseT;
import com.power.domain.ERRORCODE;
import com.power.domain.PlatformInfo;
import com.power.domain.UserPlatform;
import com.power.facade.*;
import com.power.test.BaseTest;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.util.xml.XStreamTransformer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2017/7/11.
 */
public class TestWechat extends BaseTest{

    private final static Logger logger = LoggerFactory.getLogger(TestWechat.class);

    String uniqueKey = "powertest";
    String openId = "oqoGE0VhoE0ouB1ArW4LNl_iDl-s";
    @Autowired
    private MockMvc mvc;

    private static final String ok_rtn = "0000000";


    @Autowired
    private IUserPlatformFacade userPlatformFacade;

    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private IUserAccountFacade userAccountFacade;


    @Autowired
    private IUserExpandFacade userExpandFacade;

    /**
     * 关注事件
     * @throws Exception
     */
    @Test
    public void testSubscribeWechat() throws Exception {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String nonce = "123";

        String rtnString = this.mvc.perform(post("/wechat/callback/{uniqueKey}/callback",uniqueKey)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("signature",SHA1.gen(wxMpService.getWxMpConfigStorage().getToken(),timestamp,nonce))
                .param("timestamp",timestamp)
                .param("nonce",nonce)
                .content(genWechatUserXml(WxConsts.EVT_SUBSCRIBE)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.isTrue(!StringUtils.isEmpty(rtnString),"关注消息推送为空");

        logger.info(rtnString);

        Map<String,Object> params = Maps.newHashMap();
        PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(uniqueKey);
        Assert.notNull(platformInfo,"公众号为空");
        params.put("openId",openId);
        params.put("platformId",platformInfo.getId());

        List<UserPlatform> userPlatforms =  userPlatformFacade.getMainService().viewList(params);
        Assert.notEmpty(userPlatforms,"用户注册失败");
        Assert.isTrue(userPlatforms.size()>0,"同一公众号注册两次");

        UserPlatform userPlatform = userPlatforms.get(0);
        Assert.isTrue(userPlatform.getStatus().intValue()==1,"用户关注失败");

        Assert.notNull(userFacade.getMainService().view(userPlatform.getUserId()),"用户唯一表注册失败");

    }
    /**
     * 取消关注事件
     * @throws Exception
     */
    @Test
    public void testUnSubscribeWechat() throws Exception {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String nonce = "123";

        String rtnString = this.mvc.perform(post("/wechat/callback/{uniqueKey}/callback",uniqueKey)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("signature",SHA1.gen(wxMpService.getWxMpConfigStorage().getToken(),timestamp,nonce))
                .param("timestamp",timestamp)
                .param("nonce",nonce)
                .content(genWechatUserXml(WxConsts.EVT_UNSUBSCRIBE)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.isTrue(!StringUtils.isEmpty(rtnString),"关注消息推送为空");

        Map<String,Object> params = Maps.newHashMap();
        PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(uniqueKey);
        Assert.notNull(platformInfo,"公众号为空");

        params.put("openId",openId);
        params.put("platformId",platformInfo.getId());

        List<UserPlatform> userPlatforms =  userPlatformFacade.getMainService().viewList(params);
        Assert.notEmpty(userPlatforms,"用户不存在");
        Assert.isTrue(userPlatforms.size()>0,"用户数据异常");

        UserPlatform userPlatform = userPlatforms.get(0);
        Assert.isTrue(userPlatform.getStatus().intValue()==0,"用户取消关注成功");

    }

    /**
     * 测试获取JSAPI权限
     * @throws Exception
     */
    @Test
    public void testJsApiWechat() throws Exception {
        String url= "/platform/wechat";
        String json = this.mvc.perform(post("/platform/wechat/{uniqueKey}/jsApi",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("url",url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode").value(ok_rtn))
                .andReturn().getResponse().getContentAsString();

        WxJsapiSignature wxJsapiSignature = JSON.parseObject(JSON.parseObject(json, ResponseT.class).getBizData().toString(),WxJsapiSignature.class);
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        Assert.isTrue(wxJsapiSignature.getSignature().equals(SHA1.genWithAmple("jsapi_ticket=" + wxMpService.getJsapiTicket(false),
                "noncestr=" + wxJsapiSignature.getNonceStr(), "timestamp=" + wxJsapiSignature.getTimestamp(), "url=" + url)),"微信jsAPI校验错误");
    }

    /**
     *
     * <xml>
     * <ToUserName><![CDATA[toUser]]></ToUserName>
     * <FromUserName><![CDATA[FromUser]]></FromUserName>
     * <CreateTime>123456789</CreateTime>
     * <MsgType><![CDATA[event]]></MsgType>
     * <Event><![CDATA[subscribe]]></Event>
     * </xml>
     * @return
     */
    private String genWechatUserXml(String wxConsts){
        WxMpXmlMessage wxMpXmlMessage = new WxMpXmlMessage();
        wxMpXmlMessage.setToUser(uniqueKey);
        wxMpXmlMessage.setMsgType(WxConsts.XML_MSG_EVENT);
        wxMpXmlMessage.setEvent(wxConsts);
        wxMpXmlMessage.setFromUser(openId);
        wxMpXmlMessage.setCreateTime(System.currentTimeMillis()/1000);
        wxMpXmlMessage.setSendLocationInfo(null);
        wxMpXmlMessage.setScanCodeInfo(null);
        wxMpXmlMessage.setSendPicsInfo(null);
        wxMpXmlMessage.setHardWare(null);
        String rtnXml = XStreamTransformer.toXml(WxMpXmlMessage.class,wxMpXmlMessage);
        System.out.println(rtnXml);
        return rtnXml;
    }
}
