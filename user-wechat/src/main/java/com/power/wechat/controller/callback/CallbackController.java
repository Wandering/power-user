package com.power.wechat.controller.callback;

import com.google.common.collect.Maps;
import com.power.facade.IPlatformInfoFacade;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

    Logger logger = Logger.getLogger(CallbackController.class);

//    /**
//     * @param adminUser 开发者微信号
//     * @param openId 发送方帐号（一个OpenID）
//     * @param openId 消息创建时间 （整型）
//     * @param msgType 消息类型，event
//     * @param enevt 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
//     */
    @RequestMapping(value = "/{uniqueKey}/callback",method = RequestMethod.POST)
    @ResponseBody
    public String callback(@RequestParam String signature,
                         @RequestParam String timestamp,
                         @RequestParam String nonce,
                         @PathVariable String uniqueKey ,
                         HttpServletRequest request, HttpServletResponse response){
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            logger.info("非法请求， signature："+signature);
//            @RequestParam("ToUserName") String adminUser, @RequestParam("FromUserName") String openId, @RequestParam("CreateTime")String createTime, @RequestParam("MsgType")String msgType,@RequestParam("Enevt")String enevt

            return "非法请求";
        }
        try {
            PrintWriter out = response.getWriter();

        WxMpXmlMessage wxMpXmlMessage = null;
        try {
            wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
            String adminUser= wxMpXmlMessage.getToUser();
            String openId= wxMpXmlMessage.getFromUser();
            Long createTime= wxMpXmlMessage.getCreateTime();
            String msgType= wxMpXmlMessage.getMsgType();
            String rtnMsg  = "感谢使用PP充电";
        switch (msgType){
            case "event":
                String enevt= wxMpXmlMessage.getEvent();
                rtnMsg = event(adminUser,openId,createTime,msgType,enevt,uniqueKey);
                break;
            default:
                break;

        }
            out.print(rtnMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String event(String adminUser,String openId, Long createTime,String msgType,String enevt, @PathVariable String uniqueKey){
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        WxMpUser wxMpUser = null;
        try {
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
//            wxMpUser.setUnionId("11231231");
//            wxMpUser.setHeadImgUrl("headimgurl");
            /////////////////////////////////////////

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

        boolean flag = false;
        //判定关注类型
        switch (enevt){
            case "subscribe":
                logger.info("用户关注："+wxMpUser.getOpenId());
                //关注
                flag =  platformInfoFacade.wxSubscribe(wechat,uniqueKey);
                break;
            case "unsubscribe":
                logger.info("用户取消关注："+wxMpUser.getOpenId());
                //取消关注
                flag = platformInfoFacade.wxUnSubscribe(wechat,uniqueKey);
                break;
            default:
                break;
        }
        return "感谢关注PP充电";
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
