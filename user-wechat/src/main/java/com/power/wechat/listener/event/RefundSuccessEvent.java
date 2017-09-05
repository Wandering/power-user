package com.power.wechat.listener.event;

import com.power.dto.WxRefundEvent;
import com.power.yuneng.user.IVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class RefundSuccessEvent implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(RefundSuccessEvent.class);
    @Autowired
    private IVoucherService voucherService;

    public RefundSuccessEvent() {
    }

    /**
     * 用户退款事件
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        WxRefundEvent wxRefundEvent = (WxRefundEvent) arg;
        //查询当前用户所有的未结算的金额
        logger.info("推送退款成功模板！");
        voucherService.sendTemplateMsg(wxRefundEvent.getUniqueKey(),"FILL_BALANCE",wxRefundEvent.getOpenId(),new ArrayList<>());
    }
}
