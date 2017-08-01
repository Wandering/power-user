package com.power.test.wechat;

import com.alibaba.fastjson.JSON;
import com.power.enums.PowerEvent;
import com.power.test.BaseTest;
import com.power.wechat.listener.EventObservableFactory;
import com.power.wechat.util.WxMpServiceUtil;
import com.power.yuneng.user.IArticleService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.builder.outxml.NewsBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Administrator on 2017/7/18.
 */
public class TestPushMessage extends BaseTest {
    Logger logger = LoggerFactory.getLogger(TestPushMessage.class);
    /**
     * 测试获取微信首关推送消息
     * <xml>
     * <ToUserName><![CDATA[toUser]]></ToUserName>
     * <FromUserName><![CDATA[fromUser]]></FromUserName>
     * <CreateTime>12345678</CreateTime>
     * <MsgType><![CDATA[news]]></MsgType>
     * <ArticleCount>2</ArticleCount>
     * <Articles>
     * <item>
     * <Title><![CDATA[title1]]></Title>
     * <Description><![CDATA[description1]]></Description>
     * <PicUrl><![CDATA[picurl]]></PicUrl>
     * <Url><![CDATA[url]]></Url>
     * </item>
     * <item>
     * <Title><![CDATA[title]]></Title>
     * <Description><![CDATA[description]]></Description>
     * <PicUrl><![CDATA[picurl]]></PicUrl>
     * <Url><![CDATA[url]]></Url>
     * </item>
     * </Articles>
     * </xml>
     *
     * @throws Exception
     */
    @Test
    public void testGenMsg() throws Exception {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService("ppower");
        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_NEWS,0,10);
        if (wxMpMaterialFileBatchGetResult.getTotalCount()>0){
            WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem batchGetNewsItem = wxMpMaterialFileBatchGetResult.getItems().get(0);
            String mediaId = batchGetNewsItem.getMediaId();
            WxMpMaterialNews wxMpMaterialNews = wxMpService.getMaterialService().materialNewsInfo(mediaId);
            System.out.println(wxMpMaterialNews.toString());
//
//            WxMpMassOpenIdsMessage wxMpMassOpenIdsMessage = new WxMpMassOpenIdsMessage();
//            wxMpMassOpenIdsMessage.setMediaId(mediaId);
            String openId = "o9P_pv2gzYtOm6V_sDNhZ7HLWHyY";
//            List<String> openIds = new ArrayList<>();
//            openIds.add(openId);
//            openIds.add("o9P_pv7DEIfZuy_5-Mf6b-9JsGBo");
//            wxMpMassOpenIdsMessage.setToUsers(openIds);
//            wxMpMassOpenIdsMessage.setMsgType(WxConsts.MATERIAL_NEWS);
//            wxMpMassOpenIdsMessage.setContent(wxMpMaterialNews.getArticles().get(0).getContent());
//            wxMpService.massOpenIdsMessageSend(wxMpMassOpenIdsMessage);


            if (wxMpMaterialNews.isEmpty()){}
            else {
                NewsBuilder builder = WxMpXmlOutMessage.NEWS().fromUser("123")
                        .toUser("o9P_pv2gzYtOm6V_sDNhZ7HLWHyY");


                for (WxMpMaterialNews.WxMpMaterialNewsArticle newsArticle : wxMpMaterialNews.getArticles()) {
                    WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                    WxMpMaterialNews.WxMpMaterialNewsArticle wxMpMaterialNewsArticle = wxMpMaterialNews.getArticles().get(0);
                    item.setDescription(newsArticle.getDigest());
                    item.setPicUrl(newsArticle.getThumbUrl());
                    item.setTitle(newsArticle.getTitle());
                    item.setUrl(newsArticle.getUrl());
                    builder.addArticle(item);
                }
                WxMpXmlOutNewsMessage m = builder.build();
                logger.info(JSON.toJSON(m).toString());
            }


        }


//        WxMpMaterialNews wxMpMaterialNews = wxMpService.getMaterialService().materialNewsInfo("mp100000003");
//        logger.info(JSON.toJSONString(wxMpMaterialNews));
//        Assert.isTrue(wxJsapiSignature.getSignature().equals(SHA1.genWithAmple("jsapi_ticket=" + wxMpService.getJsapiTicket(false),
//                "noncestr=" + wxJsapiSignature.getNonceStr(), "timestamp=" + wxJsapiSignature.getTimestamp(), "url=" + url)),"微信jsAPI校验错误");
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    IArticleService articleService;
    /**
     * 测试微信推送消息
     *
     * @throws Exception
     */
    @Test
    public void testSendMessage() throws Exception {
        String openId = "oqoGE0UJGJSuFrSbJX-SwcpVhLYY";
        String uniqueKey = "powertest";
//        String openId = "o9P_pv2gzYtOm6V_sDNhZ7HLWHyY";
//        String uniqueKey = "ppower";
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        String mediaId = wxMpService.getMaterialService().materialNewsBatchGet(0,1).getItems().get(0).getMediaId();
        articleService.sendArticle(uniqueKey,openId,mediaId);
    }


    /**
     * 测试事件监听
     *
     * @throws Exception
     */
    @Test
    public void testEvent() throws Exception {
        String openId = "o9P_pv2gzYtOm6V_sDNhZ7HLWHyY";
        String uniqueKey = "ppower";
        this.mvc.perform(post("/platform/wechat/{uniqueKey}/event",uniqueKey).accept(MediaType.APPLICATION_JSON_UTF8)
                .param("openId",openId)
                .param("event","RETURN_END"))
                .andExpect(status().isOk()
                )
        ;
    }
}
