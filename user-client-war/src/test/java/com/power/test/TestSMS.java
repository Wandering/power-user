package com.power.test;

import com.power.sms.api.SMSService;
import com.power.sms.domain.SMSCheckCode;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2017/6/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestSMS extends TestCase{
    @MockBean
    SMSService smsService;
    Logger logger = Logger.getLogger(TestSMS.class);
    @Test
    public void testSMS(){
        SMSCheckCode smsCheckCode = new SMSCheckCode();
        smsCheckCode.setPhone("17602903609");
        smsCheckCode.setCheckCode("123456");
        smsService.sendSMS(smsCheckCode,false);
    }
}
