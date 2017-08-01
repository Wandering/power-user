package com.power.yuneng.user;

import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
public interface IArticleService {
    /**
     * 根据信息ID发送图文消息
     * @param uniqueKey
     * @param openId
     * @param mediaId
     * @return
     */
    boolean sendArticle(String uniqueKey, String openId, String mediaId);
    boolean sendArticle(String uniqueKey, String openId,String title,String desc, String picurl, String url);
}
