package com.bitsco.vks.category.cache;

import com.bitsco.vks.category.entities.*;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.model.Otp;
import com.bitsco.vks.common.response.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: truongnq
 * @date: 06-May-19 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CacheService {
    @Autowired
    RedisTemplate<String, Otp> otpRedis;

    @Autowired
    RedisTemplate<String, User> userRedis;

    @Autowired
    RedisTemplate<String, Param> paramRedis;

    @Autowired
    RedisTemplate<String, Token> tokenRedis;

    @Autowired
    RedisTemplate<String, Province> provinceRedis;

    @Autowired
    RedisTemplate<String, District> districtRedis;

    @Autowired
    RedisTemplate<String, Commune> communeRedis;

    @Autowired
    HttpServletRequest request;

    public void addOtp2RedisCache(Otp otp) {
        final ValueOperations<String, Otp> opsForValue = otpRedis.opsForValue();
        String key = otp.getCode();
        opsForValue.set(key, otp, otp.getExpiry(), TimeUnit.MILLISECONDS);
    }

    public Otp getOtpFromCache(String otpCode) {
        final ValueOperations<String, Otp> opsForValue = otpRedis.opsForValue();
        if (otpRedis.hasKey(otpCode))
            return opsForValue.get(otpCode);
        else return null;
    }

    public void addParam2RedisCache(Param param) {
        final ValueOperations<String, Param> opsForValue = paramRedis.opsForValue();
        String key = param.getGroup().toUpperCase() + Constant.DASH + param.getCode().toUpperCase();
        opsForValue.set(key, param, 365, TimeUnit.DAYS);
    }

    public String getValueParamFromCache(String group, String code) {
        final ValueOperations<String, Param> opsForValue = paramRedis.opsForValue();
        String multiKey = group.trim().toUpperCase() + Constant.DASH + code.trim().toUpperCase();
        if (paramRedis.hasKey(multiKey))
            return opsForValue.get(multiKey).getValue();
        else return null;
    }


    public Param getParamFromCache(String group, String code) {
        final ValueOperations<String, Param> opsForValue = paramRedis.opsForValue();
        String multiKey = group.trim().toUpperCase() + Constant.DASH + code.trim().toUpperCase();
        if (paramRedis.hasKey(multiKey))
            return opsForValue.get(multiKey);
        else return null;
    }

    public int getIntParamFromCache(String group, String code, int defaultValue) {
        final ValueOperations<String, Param> opsForValue = paramRedis.opsForValue();
        String multiKey = group.trim().toUpperCase() + Constant.DASH + code.trim().toUpperCase();
        if (paramRedis.hasKey(multiKey)) {
            Param param = opsForValue.get(multiKey);
            if (param.getStatus() == Constant.STATUS_OBJECT.ACTIVE)
                try {
                    return Integer.valueOf(param.getValue());
                } catch (Exception e) {
                    return defaultValue;
                }
        }
        return defaultValue;
    }

    public long getLongParamFromCache(String group, String code, long defaultValue) {
        final ValueOperations<String, Param> opsForValue = paramRedis.opsForValue();
        String multiKey = group.trim().toUpperCase() + Constant.DASH + code.trim().toUpperCase();
        if (paramRedis.hasKey(multiKey)) {
            Param param = opsForValue.get(multiKey);
            if (param.getStatus() == Constant.STATUS_OBJECT.ACTIVE)
                try {
                    return Long.valueOf(param.getValue());
                } catch (Exception e) {
                    return defaultValue;
                }
        }
        return defaultValue;
    }

    public void addUser2RedisCache(User user) {
        final ValueOperations<String, User> opsForValue = userRedis.opsForValue();
        String key = Constant.TABLE.USERS + Constant.DASH + user.getUsername().trim().toLowerCase();
        opsForValue.set(key, user, 365, TimeUnit.DAYS);
    }

    public User getUserFromCache(String username) {
        final ValueOperations<String, User> opsForValue = userRedis.opsForValue();
        String key = Constant.TABLE.USERS + Constant.DASH + username.trim().toLowerCase();
        if (userRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public boolean deleteUserFromCache(long userId) {
        return userRedis.delete(Constant.TABLE.USERS + Constant.DASH + userId);
    }

    public void addProvince2RedisCache(Province province) {
        final ValueOperations<String, Province> opsForValue = provinceRedis.opsForValue();
        String key = Constant.TABLE.PROVINCE + Constant.DASH + province.getCode();
        opsForValue.set(key, province, 365, TimeUnit.DAYS);
    }

    public Province getProvinceFromCache(String provinceCode) {
        final ValueOperations<String, Province> opsForValue = provinceRedis.opsForValue();
        if (otpRedis.hasKey(Constant.TABLE.PROVINCE + Constant.DASH + provinceCode))
            return opsForValue.get(Constant.TABLE.PROVINCE + Constant.DASH + provinceCode);
        else return null;
    }

    public boolean deleteProvinceFromCache(String provinceCode) {
        return provinceRedis.delete(Constant.TABLE.PROVINCE + Constant.DASH + provinceCode);
    }

    public void addDistrict2RedisCache(District district) {
        final ValueOperations<String, District> opsForValue = districtRedis.opsForValue();
        String key = Constant.TABLE.DISTRICT + Constant.DASH + district.getCode();
        opsForValue.set(key, district, 365, TimeUnit.DAYS);
    }

    public District getDistrictFromCache(String districtCode) {
        final ValueOperations<String, District> opsForValue = districtRedis.opsForValue();
        if (otpRedis.hasKey(Constant.TABLE.DISTRICT + Constant.DASH + districtCode))
            return opsForValue.get(Constant.TABLE.DISTRICT + Constant.DASH + districtCode);
        else return null;
    }

    public boolean deleteDistrictFromCache(String districtCode) {
        return districtRedis.delete(Constant.TABLE.DISTRICT + Constant.DASH + districtCode);
    }

    public void addCommune2RedisCache(Commune commune) {
        final ValueOperations<String, Commune> opsForValue = communeRedis.opsForValue();
        String key = Constant.TABLE.COMMUNE + Constant.DASH + commune.getCode();
        opsForValue.set(key, commune, 365, TimeUnit.DAYS);
    }

    public Commune getCommuneFromCache(String communeCode) {
        final ValueOperations<String, Commune> opsForValue = communeRedis.opsForValue();
        if (otpRedis.hasKey(Constant.TABLE.COMMUNE + Constant.DASH + communeCode))
            return opsForValue.get(Constant.TABLE.COMMUNE + Constant.DASH + communeCode);
        else return null;
    }

    public boolean deleteCommuneFromCache(String communeCode) {
        return communeRedis.delete(Constant.TABLE.COMMUNE + Constant.DASH + communeCode);
    }

    public String getHeaderValue(String headerName) {
        try {
            return request.getHeader(headerName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromHeader() {
        return getHeaderValue(Constant.KEY.USERNAME);
    }

}
