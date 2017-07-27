package com.power.test.sms;

import com.power.test.BaseTest;
import com.power.yuneng.sms.api.ISMSService;
import com.power.yuneng.sms.domain.SMSCheckCode;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Created by Administrator on 2017/6/13.
 */
public class TestSMS extends BaseTest {
    @Autowired
    ISMSService smsService;
    Logger logger = Logger.getLogger(TestSMS.class);
    @Test
    public void testSMS(){
        SMSCheckCode smsCheckCode = new SMSCheckCode();
        smsCheckCode.setPhone("17602903609");
        smsCheckCode.setCheckCode("123456");
        smsCheckCode.setBizTarget("123456");
        boolean flag = smsService.sendSMS(smsCheckCode);
        Assert.isTrue(flag,"短信发送成功");
    }
}
