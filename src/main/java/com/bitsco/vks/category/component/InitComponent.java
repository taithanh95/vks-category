package com.bitsco.vks.category.component;

import com.bitsco.vks.category.service.RedisService;
import com.bitsco.vks.common.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitComponent {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.APPLICATION);
    @Autowired
    RedisService redisService;

    @PostConstruct
    public void initCache() {
        try {
            LOGGER.info("pushAllUserToRedisCache >>> " + redisService.pushAllUserToRedisCache());
        } catch (Exception e) {
            LOGGER.error("Exception when pushAllUserToRedisCache", e);
        }
        try {
            LOGGER.info("pushAllParamToRedisCache >>> " + redisService.pushAllParamToRedisCache());
        } catch (Exception e) {
            LOGGER.error("Exception when pushAllParamToRedisCache", e);
        }
        try {
            LOGGER.info("pushAreaUserToRedisCache >>> " + redisService.pushAllAreaToRedisCache());
        } catch (Exception e) {
            LOGGER.error("Exception when pushAreaUserToRedisCache", e);
        }
    }
}
