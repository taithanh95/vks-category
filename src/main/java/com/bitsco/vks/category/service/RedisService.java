package com.bitsco.vks.category.service;

public interface RedisService {
    boolean pushAllParamToRedisCache() throws Exception;

    boolean pushAllUserToRedisCache() throws Exception;

    boolean pushAllAreaToRedisCache() throws Exception;

    boolean deleteAllUserFromRedisCache() throws Exception;

    boolean deleteAllAreaFromRedisCache() throws Exception;
}
