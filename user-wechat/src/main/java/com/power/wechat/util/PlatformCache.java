package com.power.wechat.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.power.domain.PlatformInfo;
import com.power.facade.IPlatformInfoFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/10.
 */
@Component
public class PlatformCache {
    Logger logger = Logger.getLogger(PlatformCache.class);
    @Autowired
    private IPlatformInfoFacade facade;
    LoadingCache<String, PlatformInfo> platformInfoLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .refreshAfterWrite(1, TimeUnit.HOURS)
            .build(
                    new CacheLoader<String, PlatformInfo>() {
                        public PlatformInfo load(String uniqueKey){
                            return facade.getPlatformInfoByUniqueKey(uniqueKey);
                        }
                    });


    public PlatformInfo getCache(String uniqueKey) {
        try {
            return platformInfoLoadingCache.get(uniqueKey);
        } catch (Exception e) {
            logger.error("no search PlatformInfo!");
            return null;
        }
    }
}
