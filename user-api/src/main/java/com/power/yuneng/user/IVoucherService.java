package com.power.yuneng.user;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public interface IVoucherService {
    boolean sendTemplateMsg(String uniqueKey,  String openId, String templateId,List<WxMpTemplateData> wxMpTemplateDatas);
}
