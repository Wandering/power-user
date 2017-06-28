package com.power.common;

import com.alibaba.fastjson.JSON;
import com.power.core.cache.RedisRepository;
import com.power.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/16.
 */
@Component
public class UserRedisCache {
    @Autowired
    private RedisRepository<String,String> redis;
    private static final int USER_INFO_TIME_OUT = 7;//7天
    public void putUserInfoDto(String token, UserInfoDTO userInfoDTO){
        if (!redis.exists(token)) {
            redis.set(token, JSON.toJSONString(userInfoDTO));
            //用户信息保持7天
            redis.expire(token, USER_INFO_TIME_OUT, TimeUnit.DAYS);
        }else {
            redis.getSet(token, JSON.toJSONString(userInfoDTO));
        }
    }
}
