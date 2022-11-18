package com.bitsco.vks.category.thread;

import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.*;
import com.bitsco.vks.category.repository.UserRepository;
import com.bitsco.vks.common.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadCache {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.THREAD);
    @Autowired
    UserRepository userRepository;

    @Autowired
    CacheService cacheService;

    ExecutorService executorService;

    @PostConstruct
    public void init() {
        int numThreads = 100;
        executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void addParam2RedisCache(List<Param> paramList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long id = System.currentTimeMillis();
                try {
                    for (Param u : paramList)
                        cacheService.addParam2RedisCache(u);
                } catch (Exception e) {
                    LOGGER.info("[Exception][" + id + "] when addUser2RedisCache ", e);
                } finally {
                    LOGGER.info("[Duration = " + (System.currentTimeMillis() - id) + "] addUser2RedisCache");
                }
            }
        });
    }

    public void addUser2RedisCache(List<User> userList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long id = System.currentTimeMillis();
                try {
                    for (User u : userList)
                        cacheService.addUser2RedisCache(u);
                } catch (Exception e) {
                    LOGGER.info("[Exception][" + id + "] when addUser2RedisCache ", e);
                } finally {
                    LOGGER.info("[Duration = " + (System.currentTimeMillis() - id) + "] addUser2RedisCache");
                }
            }
        });
    }

    public void addProvince2RedisCache(List<Province> provinceList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long id = System.currentTimeMillis();
                try {
                    for (Province province : provinceList)
                        cacheService.addProvince2RedisCache(province);
                } catch (Exception e) {
                    LOGGER.info("[Exception][" + id + "] when addProvince2RedisCache ", e);
                } finally {
                    LOGGER.info("[Duration = " + (System.currentTimeMillis() - id) + "] addProvince2RedisCache");
                }
            }
        });
    }

    public void addDistrict2RedisCache(List<District> districtList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long id = System.currentTimeMillis();
                try {
                    for (District district : districtList)
                        cacheService.addDistrict2RedisCache(district);
                } catch (Exception e) {
                    LOGGER.info("[Exception][" + id + "] when addDistrict2RedisCache ", e);
                } finally {
                    LOGGER.info("[Duration = " + (System.currentTimeMillis() - id) + "] addDistrict2RedisCache");
                }
            }
        });
    }

    public void addCommune2RedisCache(List<Commune> communeList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long id = System.currentTimeMillis();
                try {
                    for (Commune commune : communeList)
                        cacheService.addCommune2RedisCache(commune);
                } catch (Exception e) {
                    LOGGER.info("[Exception][" + id + "] when addCommune2RedisCache ", e);
                } finally {
                    LOGGER.info("[Duration = " + (System.currentTimeMillis() - id) + "] addCommune2RedisCache");
                }
            }
        });
    }
}
