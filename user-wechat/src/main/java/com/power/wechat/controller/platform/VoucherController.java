package com.power.wechat.controller.platform;

import com.alibaba.fastjson.JSONArray;
import com.power.core.exception.BizException;
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
@RequestMapping("/platform/wechat/voucher")
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    @Autowired
    IVoucherService voucherService;

    /**
     * <pre>
     *     tempData = L
     * </pre>
     * @param uniqueKey 微信公众号对于宇能平台唯一key
     * @param templateId 模板消息ID
     * @param openId 微信公众号openId
     * @param tempData 模板数据
     * @return
     */
    @PostMapping("/{uniqueKey}/sendTemplateMsg")
    public boolean sendTemplateMsg(@PathVariable String uniqueKey,@RequestParam String templateId,@RequestParam String openId,@RequestParam String tempData){
        if (tempData == null){
            throw new BizException("error","模板参数为空");
        }
        List<WxMpTemplateData> list = JSONArray.parseArray(tempData,WxMpTemplateData.class);

        return voucherService.sendTemplateMsg(uniqueKey,templateId,openId,list);
    }

}
