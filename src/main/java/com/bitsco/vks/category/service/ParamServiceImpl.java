package com.bitsco.vks.category.service;

import com.bitsco.vks.category.repository.ParamRepository;
import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.Param;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {
    @Autowired
    ParamRepository paramRepository;

    @Autowired
    CacheService cacheService;

    @Override
    public Param createOrUpdate(Param param) throws Exception {
        ValidateCommon.validateNullObject(param.getGroup(), "group");
        ValidateCommon.validateNullObject(param.getCode(), "code");
        ValidateCommon.validateNullObject(param.getValue(), "value");
        if (param.getGroup().contains(Constant.BLANK))
            throw new CommonException(Response.DATA_INVALID, "Param.group không được chứa khoảng trắng");
        if (param.getCode().contains(Constant.BLANK))
            throw new CommonException(Response.DATA_INVALID, "Param.code không được chứa khoảng trắng");
        Param paramOld = findFirstByGroupAndCode(param);
        if (paramOld != null)
            param = paramRepository.save(paramOld.coppyFrom(param));
        else {
            param.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            param = paramRepository.save(param);
        }
        cacheService.addParam2RedisCache(param);
        return param;
    }

    @Override
    public Param findFirstByGroupAndCode(Param param) throws Exception {
        ValidateCommon.validateNullObject(param.getGroup(), "group");
        ValidateCommon.validateNullObject(param.getCode(), "code");
        return paramRepository.findFirstByGroupAndCode(param.getGroup().trim().toUpperCase(), param.getCode().trim().toUpperCase());
    }

    @Override
    public List<Param> findByGroup(Param param) throws Exception {
        ValidateCommon.validateNullObject(param.getGroup(), "group");
        return paramRepository.findByGroupAndStatus(param.getGroup().trim().toUpperCase(), Constant.STATUS_OBJECT.ACTIVE);
    }

    @Override
    public List<Param> getListParam(Param param) throws Exception {
        List<Param> paramList = paramRepository.getListParam(
                StringCommon.isNullOrBlank(param.getGroup()) ? null : StringCommon.addLikeRightAndLeft(param.getGroup()),
                StringCommon.isNullOrBlank(param.getCode()) ? null : StringCommon.addLikeRightAndLeft(param.getCode()),
                StringCommon.isNullOrBlank(param.getValue()) ? null : param.getValue(),
                StringCommon.isNullOrBlank(param.getName()) ? null : StringCommon.addLikeRightAndLeft(param.getName()),
                param.getType(),
                param.getStatus());
        if (ArrayListCommon.isNullOrEmpty(paramList)) throw new CommonException(Response.DATA_NOT_FOUND);
        return paramList;
    }

}
