package com.power.wechat.listener.event;

import com.power.dto.WxEvent;
import com.power.yuneng.user.IVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class RefundAcceptMsgEvent implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(RefundAcceptMsgEvent.class);
    @Autowired
    private IVoucherService voucherService;


    public RefundAcceptMsgEvent() {
    }

    /**
     * 用户退款受理推送通知
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        WxEvent wxEvent = (WxEvent) arg;
        //查询当前用户所有的未结算的金额
        logger.info("推送退款受理模板！");
        voucherService.sendTemplateMsg(wxEvent.getUniqueKey(),wxEvent.getOpenId(),"FILL_BALANCE",new ArrayList<>());
    }
}
