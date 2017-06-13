package com.power.wechat.controller.login;

import com.alibaba.fastjson.JSON;
import com.power.core.cache.RedisRepository;
import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.facade.IUserFacade;
import com.power.sms.api.SMSService;
import com.power.sms.domain.SMSCheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/13.
 * 认证用户手机号码
 */
@RestController
@RequestMapping("/wechat/auth")
public class AuthController {
    @Autowired
    private SMSService smsService;

    @Autowired
    private RedisRepository<String,String> repository;

    private final static String USER_SMS = "USER_SMS_";
    private final static String USER_SMS_IN_USE_TAG = "USER_SMS__IN_USE_TAG_";

    private final static int TIME_OUT = 5 * 60;
    private final static int TIME_OUT_LOCK = 60;

    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final static Pattern PHONE_CHECK = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号

    @Autowired
    private IUserFacade userFacade;

    @RequestMapping(value = "/{uniqueKey}/user/sendSms")
    @ResponseBody
    public boolean sendSms(@RequestParam("phone") String phone){
        if (!PHONE_CHECK.matcher(phone).matches()){
            throw new BizException(ERRORCODE.SMS_CHECK_PHONE.getCode(),ERRORCODE.SMS_CHECK_PHONE.getMessage());
        }
        String redisKey = USER_SMS+phone;
        String redisLockKey = USER_SMS_IN_USE_TAG+phone;
        //redis存储验证码
        if (!repository.exists(redisLockKey)) {

            SMSCheckCode smsCheckCode = new SMSCheckCode();
            smsCheckCode.setPhone(phone);
            String code = String.valueOf((Math.random() * 9 + 1) * 100000);
            smsCheckCode.setCheckCode(code);
            boolean flag = smsService.sendSMS(smsCheckCode, false);
            if (flag) {
                //存储值
                repository.set(redisKey, code);
                repository.expire(redisKey, TIME_OUT, TIME_UNIT);
                //存储锁
                repository.set(redisLockKey, code);
                repository.expire(redisLockKey, TIME_OUT_LOCK, TIME_UNIT);
            }else {
                throw new BizException(ERRORCODE.SMS_CHECK_FAIL.getCode(),ERRORCODE.SMS_CHECK_FAIL.getMessage());
            }
        }else {
            throw new BizException(ERRORCODE.SMS_CHECK_EXIST.getCode(),ERRORCODE.SMS_CHECK_EXIST.getMessage());
        }
        return false;
    }

    @RequestMapping(value = "/{uniqueKey}/user/checkSms")
    @ResponseBody
    public boolean checkSms(@PathVariable("uniqueKey")String uniqueKey,
                            @RequestParam String phone,
                            @RequestParam String checkCode,
                            @RequestParam String token){
        if (!PHONE_CHECK.matcher(phone).matches()){
            throw new BizException(ERRORCODE.SMS_CHECK_PHONE.getCode(),ERRORCODE.SMS_CHECK_PHONE.getMessage());
        }
        String redisKey = USER_SMS+phone;
        if (repository.exists(redisKey)){
            String code = repository.get(redisKey);
            if (code.equals(checkCode)){
                //TODO 待优化至拦截器
                UserAccount userAccount = JSON.parseObject(repository.get(token),UserAccount.class);
                User user = (User)userFacade.getMainService().view(userAccount.getUserId());
                user.setPhone(phone);

                return true;
            }
            throw new BizException(ERRORCODE.SMS_CHECK_ERROR.getCode(),ERRORCODE.SMS_CHECK_ERROR.getMessage());
        }
        throw new BizException(ERRORCODE.SMS_CHECK_ERROR.getCode(),ERRORCODE.SMS_CHECK_ERROR.getMessage());
    }


}