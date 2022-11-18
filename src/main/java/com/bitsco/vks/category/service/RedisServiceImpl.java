package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.*;
import com.bitsco.vks.category.repository.*;
import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.thread.ThreadCache;
import com.bitsco.vks.common.util.ArrayListCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RedisServiceImpl implements RedisService {
    @Autowired
    ParamRepository paramRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CommuneRepository communeRepository;

    @Autowired
    ThreadCache threadCache;

    @Autowired
    CacheService cacheService;

    @Override
    public boolean pushAllParamToRedisCache() throws Exception {
        List<Param> paramList = paramRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(paramList))
            threadCache.addParam2RedisCache(paramList);
        return true;
    }

    @Override
    public boolean pushAllUserToRedisCache() throws Exception {
        List<User> userList = userRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(userList))
            threadCache.addUser2RedisCache(userList);
        return true;
    }

    @Override
    public boolean pushAllAreaToRedisCache() throws Exception {
        List<Province> provinceList = provinceRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(provinceList))
            threadCache.addProvince2RedisCache(provinceList);
        List<District> districtList = districtRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(districtList))
            threadCache.addDistrict2RedisCache(districtList);
        List<Commune> communeList = communeRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(communeList))
            threadCache.addCommune2RedisCache(communeList);
        return true;
    }

    @Override
    public boolean deleteAllUserFromRedisCache() throws Exception {
        List<User> userList = userRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(userList))
            userList.stream().forEach(x -> {
                cacheService.deleteUserFromCache(x.getId());
            });
        return true;
    }

    @Override
    public boolean deleteAllAreaFromRedisCache() throws Exception {
        List<Province> provinceList = provinceRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(provinceList))
            provinceList.stream().forEach(x -> {
                cacheService.deleteProvinceFromCache(x.getCode());
            });
        List<District> districtList = districtRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(districtList))
            districtList.stream().forEach(x -> {
                cacheService.deleteDistrictFromCache(x.getCode());
            });
        List<Commune> communeList = communeRepository.findAll();
        if (!ArrayListCommon.isNullOrEmpty(communeList))
            communeList.stream().forEach(x -> {
                cacheService.deleteCommuneFromCache(x.getCode());
            });
        return true;
    }

}
