package com.power.wechat.controller.callback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.domain.PlatformInfo;
import com.power.facade.IPlatformInfoFacade;
import com.power.wechat.controller.login.LoginController;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.builder.outxml.NewsBuilder;
import me.chanjar.weixin.mp.util.xml.XStreamTransformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/9.
 */
@RestController
@RequestMapping("/wechat/callback")
public class CallbackController {

    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机字符
     * @param uniqueKey 公众号唯一标识
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{uniqueKey}/callback",method = RequestMethod.POST)
    @ResponseBody
    public void callback(@RequestParam String signature,
                         @RequestParam String timestamp,
                         @RequestParam String nonce,
                         @PathVariable String uniqueKey ,
                         HttpServletRequest request, HttpServletResponse response){

        try {
            PrintWriter out = response.getWriter();

            WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
            if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
                logger.info("非法请求， signature："+signature);
//            @RequestParam("ToUserName") String adminUser, @RequestParam("FromUserName") String openId, @RequestParam("CreateTime")String createTime, @RequestParam("MsgType")String msgType,@RequestParam("Enevt")String enevt

                out.print("非法请求");
            }


            WxMpXmlMessage wxMpXmlMessage = null;
            try {
                wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info(JSON.toJSONString(wxMpXmlMessage));
            String adminUser = wxMpXmlMessage.getToUser();
            String openId = wxMpXmlMessage.getFromUser();
            Long createTime = wxMpXmlMessage.getCreateTime();
            String msgType = wxMpXmlMessage.getMsgType();
            /////////////////////////////////////////////////
//            String adminUser= "";
//            String openId= "";
//            Long createTime= 1l;
//            String msgType= WxConsts.XML_MSG_EVENT;
            //////////////////////////////////////////
            WxMpXmlOutMessage rtnMsg = null;
            switch (msgType) {
                case WxConsts.XML_MSG_EVENT:
                    String enevt = wxMpXmlMessage.getEvent();
//                String enevt= WxConsts.EVT_SUBSCRIBE;
                    rtnMsg = event(adminUser, openId, createTime, msgType, enevt, uniqueKey);
                    break;
                default:
//                    rtnMsg = WxMpXmlOutMessage.TEXT()
//                            .content("您好,我还不认识您的消息哦~")
//                            .fromUser(adminUser)
//                            .toUser(openId)
//                            .build();
                    break;

            }
//            logger.debug(rtnMsg.toXml());
            out.print(StringUtils.isNotEmpty(rtnMsg==null?null:rtnMsg.toXml())?rtnMsg.toXml():"");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param adminUser 开发者微信号
     * @param openId 发送方帐号（一个OpenID）
     * @param openId 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param enevt 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    private WxMpXmlOutMessage event(String adminUser,String openId, Long createTime,String msgType,String enevt, @PathVariable String uniqueKey) throws WxErrorException {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        logger.debug("===============openId:{}=================",openId);
        WxMpUser wxMpUser = null;
        try {
            logger.debug("=============wxMpService========");
            logger.debug(JSON.toJSONString(wxMpService.getWxMpConfigStorage().getToken()));
//            String wxAccessToken = wxMpService.getAccessToken();
            wxMpUser = wxMpService.getUserService().userInfo(openId);
            /////////////////////////////////////////
//            wxMpUser = new WxMpUser();
//            wxMpUser.setCountry("中国");
//            wxMpUser.setProvince("陕西");
//            wxMpUser.setCity("西安");
//            wxMpUser.setOpenId("1233123123");
//            wxMpUser.setNickname("我是测试用户");
//            wxMpUser.setSex("1");
//            wxMpUser.setSexId(1);
//            wxMpUser.setUnionId("11231231");
//            wxMpUser.setHeadImgUrl("headimgurl");
            /////////////////////////////////////////

        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        logger.debug("===============wxMpUser:{}=================", JSON.toJSONString(wxMpUser));
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

        boolean flag = false;
        PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(uniqueKey);
        //判定关注类型
        switch (enevt){
            case WxConsts.EVT_SUBSCRIBE:
                logger.info("用户关注："+wxMpUser.getOpenId());
                //关注
                String rtnMsg =  platformInfoFacade.wxSubscribe(wechat,uniqueKey);
                    if (platformInfo.getFirstMediaId() !=null) {
                        WxMpMaterialNews wxMpMaterialNews = null;
                        wxMpMaterialNews = wxMpService.getMaterialService().materialNewsInfo(platformInfo.getFirstMediaId());
                        if (!wxMpMaterialNews.isEmpty()) {
                            NewsBuilder builder = WxMpXmlOutMessage.NEWS().fromUser(adminUser)
                                    .toUser(openId);
                            for (WxMpMaterialNews.WxMpMaterialNewsArticle newsArticle : wxMpMaterialNews.getArticles()) {
                                WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                                item.setDescription(newsArticle.getDigest());
                                item.setPicUrl(newsArticle.getThumbUrl());
                                item.setTitle(newsArticle.getTitle());
                                item.setUrl(newsArticle.getUrl());
                                builder.addArticle(item);
                            }
                            logger.info("推送图文消息给用户:{}",openId);
                            return builder.build();
                        }
                    }
                    return WxMpXmlOutMessage.TEXT()
                            .content(rtnMsg)
                            .fromUser(adminUser)
                            .toUser(openId)
                            .build();
            case WxConsts.EVT_UNSUBSCRIBE:
                logger.info("用户取消关注："+wxMpUser.getOpenId());
                //取消关注
                flag = platformInfoFacade.wxUnSubscribe(wechat,uniqueKey);
                break;
            default:
                break;
        }
        return null;
    }



    @RequestMapping(value = "/{uniqueKey}/callback",method = RequestMethod.GET)
    public String auth(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, String echostr, @PathVariable String uniqueKey , HttpServletRequest request, HttpServletResponse response) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            logger.info("非法请求， signature："+signature);
            return "非法请求";
        }
        if (StringUtils.isNotBlank(echostr)) {
            try {
                PrintWriter out = response.getWriter();
                out.print(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

}
