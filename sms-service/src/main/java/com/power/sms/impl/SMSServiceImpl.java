package com.power.sms.impl;

import com.power.sms.api.SMSService;
import com.power.sms.domain.SMSCheckCode;
import com.power.sms.domain.SMSSend;
import com.sun.javafx.binding.StringFormatter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by gryang on 16/05/11.
 */
@Service("SMSServiceImpl")
public class SMSServiceImpl implements SMSService, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    private List<SMSSend> sendList = new ArrayList<>();

    private List<SMSSend> sendNewList = new ArrayList<>();

    private boolean threadFlag = true;
    private static final String url = "http://sms.253.com/msg/";// 应用地址
    private static final String un = "N6882898";// 账号
    private static final String pw = "M4LuOmDTnW042b";// 密码
    /**
     * true sendList m
     * false sendNewList
     */
    private AtomicBoolean needChange = new AtomicBoolean();

    @Override
    public boolean sendSMS(SMSCheckCode smsCheckCode, boolean isChange) {
//        sms.url=http://sms.253.com/msg/
//        sms.user=N6882898
//        sms.password=M4LuOmDTnW042b
//        sms.validate.msg=【宇能共享】您好，你的验证码是:%s, 请不要告诉他人。
//        sms.rd=1

        String msg = StringFormatter.format("【宇能共享】您好，你的验证码是:%s, 请不要告诉他人", smsCheckCode.getCheckCode()).get();// 短信内容
        String rd = "1";// 是否需要状态报告，需要1，不需要0
        String ex = null;// 扩展码
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "send", false));
            method.setQueryString(new NameValuePair[] { new NameValuePair("un", un), new NameValuePair("pw", pw),
                    new NameValuePair("phone", smsCheckCode.getPhone()), new NameValuePair("rd", rd), new NameValuePair("msg", msg),
                    new NameValuePair("ex", ex), });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                String rtn = URLDecoder.decode(baos.toString(), "UTF-8");
                if (rtn!=null){
                    String[] rtns = rtn.split("\n");
                    String rtnCode =  rtns[0].split(",")[1];
                    if ("0".equals(rtnCode)){
                        return true;
                    }else {
                        logger.info("短信发送失败：{}"，);
                    }
                }else {
                    throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
                }
                return URLDecoder.decode(baos.toString(), "UTF-8")!=null;
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } catch (Exception e){

        }finally {
            method.releaseConnection();
        }
        return false;
    }

    @Override
    public boolean sendVoiceSMS(SMSCheckCode smsCheckCode, boolean isChange) {
        return true;
    }

    private String getChannel(String target) {
        if (needChange.get()) {
            for(SMSSend send : sendNewList) {
                if (send.getBizTarget().equals(target)) {
                    return send.getSendChannel();
                }
            }
        } else {
            for(SMSSend send : sendList) {
                if (send.getBizTarget().equals(target)) {
                    return send.getSendChannel();
                }
            }
        }

        return "";
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {
        threadFlag = false;
    }
}
