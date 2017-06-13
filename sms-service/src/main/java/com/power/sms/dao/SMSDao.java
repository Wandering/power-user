package com.power.sms.dao;

import com.power.sms.domain.APPSMSChannel;
import com.power.sms.domain.SMSSend;
import com.power.sms.domain.SMSSendVipCard;
import com.power.sms.domain.SMSStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gryang on 16/05/11.
 */
public interface SMSDao {
    /**
     * 保存短信信息
     * @param status
     * @return
     */
    boolean saveCheckCode(SMSStatus status);

    /**
     * 保存发送状态
     * @param status
     * @return
     */
    boolean saveSMSStatus(SMSStatus status);

    /**
     * 获取短信发送渠道信息集合
     * @return
     */
    List<SMSSend> getSendChannels();

    /**
     * 记录渠道变更信息
     * @param source
     * @param target
     * @return
     */
    boolean saveChannelChange(
            @Param("source") String source,
            @Param("target") String target
    );

    /**
     * 获取应用短信渠道关系
     * @param app
     * @return
     */
    List<APPSMSChannel> getAPPSMSChannel(@Param("app") String app);

    /**
     * 保存VIP卡号发送状态
     * @param smsSendVipCard
     * @return
     */
    boolean saveVipCard(SMSSendVipCard smsSendVipCard);
}
