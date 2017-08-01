package com.power.wechat.service.impl;

import com.power.yuneng.user.IVoucherService;
import com.power.wechat.util.WxMpServiceUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
@Service("VoucherServiceImpl")
public class VoucherServiceImpl implements IVoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    public boolean sendTemplateMsg(String uniqueKey, String templateId, String openId, List<WxMpTemplateData> wxMpTemplateDatas) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        WxMpTemplateMsgService wxMpTemplateMsgService = wxMpService.getTemplateMsgService();
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().templateId(templateId).data(wxMpTemplateDatas).toUser(openId).build();
        try {
            wxMpTemplateMsgService.sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            logger.info("信息发送失败：{}", e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
