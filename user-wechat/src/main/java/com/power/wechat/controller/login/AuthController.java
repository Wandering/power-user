package com.power.wechat.controller.login;

import com.alibaba.fastjson.JSON;
import com.power.common.UserRedisCache;
import com.power.context.UserContext;
import com.power.core.cache.RedisRepository;
import com.power.core.exception.BizException;
import com.power.domain.ERRORCODE;
import com.power.domain.PlatformInfo;
import com.power.domain.User;
import com.power.domain.UserAccount;
import com.power.dto.UserInfoDTO;
import com.power.facade.IPlatformInfoFacade;
import com.power.facade.IUserFacade;
import com.power.yuneng.activity.api.IActivityNotify;
import com.power.yuneng.activity.entity.dto.UserActivityDTO;
import com.power.yuneng.activity.entity.dto.UserActivityExDTO;
import com.power.yuneng.sms.api.ISMSService;
import com.power.yuneng.sms.domain.SMSCheckCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/user/wechat/auth")
public class AuthController {
    @Autowired
    private ISMSService smsService;

    @Autowired
    private IActivityNotify activityNotify;

    @Autowired
    private RedisRepository<String,String> repository;

    private final static String USER_SMS = "USER_SMS_";
    private final static String USER_SMS_IN_USE_TAG = "USER_SMS__IN_USE_TAG_";

    private final static int TIME_OUT = 5 * 60;
    private final static int TIME_OUT_LOCK = 60;

    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final static Pattern PHONE_CHECK = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserRedisCache userRedisCache;
    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private IPlatformInfoFacade platformInfoFacade;

    @RequestMapping(value = "/{uniqueKey}/captcha/sendSms")
    @ResponseBody
    public boolean sendSms(@RequestParam("phone") String phone,@PathVariable String uniqueKey){
        if (!PHONE_CHECK.matcher(phone).matches()){
            throw new BizException(ERRORCODE.SMS_CHECK_PHONE.getCode(),ERRORCODE.SMS_CHECK_PHONE.getMessage());
        }
        PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(uniqueKey);
        String redisKey = USER_SMS+phone;
        String redisLockKey = USER_SMS_IN_USE_TAG+phone;
        //redis存储验证码
        if (!repository.exists(redisLockKey)) {

            SMSCheckCode smsCheckCode = new SMSCheckCode();
            smsCheckCode.setBizTarget(platformInfo.getName());
            smsCheckCode.setPhone(phone);
            String code = String.valueOf((int)((Math.random() * 9 + 1) * 1000));
            smsCheckCode.setCheckCode(code);
            boolean flag = smsService.sendSMS(smsCheckCode);
            if (flag) {
                //存储值
                repository.set(redisKey, code);
                repository.expire(redisKey, TIME_OUT, TIME_UNIT);
                //存储锁
                repository.set(redisLockKey, code);
                repository.expire(redisLockKey, TIME_OUT_LOCK, TIME_UNIT);
                return true;
            }else {
                throw new BizException(ERRORCODE.SMS_CHECK_FAIL.getCode(),ERRORCODE.SMS_CHECK_FAIL.getMessage());
            }
        }else {
            throw new BizException(ERRORCODE.SMS_CHECK_EXIST.getCode(),ERRORCODE.SMS_CHECK_EXIST.getMessage());
        }
    }

    @RequestMapping(value = "/{uniqueKey}/captcha/checkSms")
    @ResponseBody
    public boolean checkSms(@PathVariable("uniqueKey")String uniqueKey,
                            @RequestParam String phone,
                            @RequestParam String checkCode,
                            @RequestParam String token,
                            @RequestParam(name = "ex",required = false) String ex){
        if (!PHONE_CHECK.matcher(phone).matches()){
            throw new BizException(ERRORCODE.SMS_CHECK_PHONE.getCode(),ERRORCODE.SMS_CHECK_PHONE.getMessage());
        }
        String redisKey = USER_SMS+phone;
        if (repository.exists(redisKey)){
            String code = repository.get(redisKey);
            if (code.equals(checkCode)){
                //TODO 待优化至拦截器
                UserInfoDTO userInfoDTO = UserContext.getCurrentUser();
                if (userInfoDTO==null){
                    throw new BizException(ERRORCODE.TOKEN_INVALID_OR_NOTHINGNESS.getCode(),ERRORCODE.TOKEN_INVALID_OR_NOTHINGNESS.getMessage());
                }
                User user = (User) userFacade.getMainService().view(userInfoDTO.getUserId());
                user.setPhone(phone);
                userInfoDTO.setPhone(phone);
                //刷新缓存
                userRedisCache.putUserInfoDto(token,userInfoDTO);
                logger.debug(JSON.toJSONString(repository.get(token)));
                userFacade.getMainService().edit(user);
                repository.del(redisKey);
                try {
                    smsNotify(ex,uniqueKey);
                }catch (Exception e){
                    logger.info(e.getMessage());
                }
                return true;
            }
            throw new BizException(ERRORCODE.SMS_CHECK_ERROR.getCode(),ERRORCODE.SMS_CHECK_ERROR.getMessage());
        }
        throw new BizException(ERRORCODE.SMS_CHECK_ERROR.getCode(),ERRORCODE.SMS_CHECK_ERROR.getMessage());
    }

    //完成事件通知
    private void smsNotify(String ex,String uniqueKey){
        if (StringUtils.isNotEmpty(ex)) {
            UserActivityExDTO userActivityExDTO = JSON.parseObject(ex, UserActivityExDTO.class);
            userActivityExDTO.setOpenId(UserContext.getCurrentUser().getOpenId());
            userActivityExDTO.setUniqueKey(uniqueKey);
            activityNotify.giveBonuses(userActivityExDTO);
        }
    }

//    public static void main(String[] args) {
//        System.out.println(String.valueOf((int)((Math.random() * 9 + 1) * 1000)));
//    }
}
