package com.power.wechat.controller.platform;

import com.alibaba.fastjson.JSONArray;
import com.power.core.exception.BizException;
import com.power.yuneng.user.IArticleService;
import com.power.yuneng.user.IVoucherService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 * 发送模板消息
 */
@RestController
@RequestMapping("/platform/wechat/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private IArticleService articleService;

    /**
     * <pre>
     *   mediaId = ""
     *
     * </pre>
     * @param uniqueKey
     * @param openId
     * @param mediaId  新闻模板ID
     * @return
     */
    @PostMapping("/{uniqueKey}/sendArticle")
    public boolean sendTemplateMsg(@PathVariable String uniqueKey,@RequestParam String openId,@RequestParam String mediaId){
        return articleService.sendArticle(uniqueKey,openId,mediaId);
    }

    /**
     * <pre>
     *   mediaId = ""
     * 发送公众号新闻
     * </pre>
     * @param uniqueKey
     * @param openId
     * @param title 标题
     * @param desc 描述
     * @param picurl  图片地址
     * @param url  文章url
     * @return
     */
    @PostMapping("/{uniqueKey}/custom/sendArticle")
    public boolean sendTemplateMsg(@PathVariable String uniqueKey,@RequestParam String openId,@RequestParam String title,@RequestParam String desc,@RequestParam String picurl,@RequestParam String url){
        return articleService.sendArticle(uniqueKey,openId,title,desc,picurl,url);
    }
}
