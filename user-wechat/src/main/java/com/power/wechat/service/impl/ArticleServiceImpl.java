package com.power.wechat.service.impl;

import com.power.wechat.util.WxMpServiceUtil;
import com.power.yuneng.user.IArticleService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.builder.kefu.NewsBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements IArticleService {
    @Override
    public boolean sendArticle(String uniqueKey, String openId, String mediaId) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        try {

            wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().mediaId(mediaId).toUser(openId).build());
            return true;
        } catch (WxErrorException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendArticle(String uniqueKey, String openId,String title,String desc, String picurl, String url) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        try {
            WxMpKefuMessage.WxArticle wxArticle  = new WxMpKefuMessage.WxArticle();
            wxArticle.setTitle(title);
            wxArticle.setDescription(desc);
            wxArticle.setPicUrl(picurl);
            wxArticle.setUrl(url);
            wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS().addArticle(wxArticle).toUser(openId).build());
            return true;
        } catch (WxErrorException e) {
            e.printStackTrace();
            return false;
        }
    }
}
