package com.bitsco.vks.category.service;

import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.*;
import com.bitsco.vks.category.feign.AuthServiceFeignAPI;
import com.bitsco.vks.category.repository.PositionGroupRoleRepository;
import com.bitsco.vks.category.repository.PositionRepository;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PositionGroupRoleRepository positionGroupRoleRepository;
    @Autowired
    AuthServiceFeignAPI authServiceFeignAPI;
    @Autowired
    CacheService cacheService;

    private Position savePosition(Position position) throws Exception {
        if (position.getId() == null)
            position.setUpdatedBy(cacheService.getUsernameFromHeader());
        else
            position.setCreatedBy(cacheService.getUsernameFromHeader());
        return positionRepository.save(position);
    }

    private PositionGroupRole savePositionGroupRole(PositionGroupRole positionGroupRole) throws Exception {
        if (positionGroupRole.getId() == null) positionGroupRole.setUpdatedBy(cacheService.getUsernameFromHeader());
        else positionGroupRole.setCreatedBy(cacheService.getUsernameFromHeader());
        return positionGroupRoleRepository.save(positionGroupRole);
    }

    @Override
    public Position createPosition(Position position) throws Exception {
        position.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return savePosition(position);
    }

    @Override
    public Position updatePosition(Position position) throws Exception {
        Position positionOld = positionRepository.findById(position.getId()).orElse(null);
        if (positionOld == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        return savePosition(positionOld.coppyFrom(position));
    }

    @Override
    public List<Position> findBySupplierIdAndUserType(Position position) throws Exception {
        ValidateCommon.validateNullObject(position.getSupplierId(), "supplierId");
        ValidateCommon.validateNullObject(position.getUserType(), "userType");
        List<Position> positionList = positionRepository.findBySupplierIdAndUserTypeAndStatusOrderById(position.getSupplierId(), position.getUserType(), Constant.STATUS_OBJECT.ACTIVE);
        if (ArrayListCommon.isNullOrEmpty(positionList)) throw new CommonException(Response.OBJECT_NOT_FOUND);
        return positionList;
    }

    @Override
    public List<Position> getListPosition(Position position) throws Exception {
        List<Position> list = positionRepository.getList(
                (position.getSupplierId() == null || position.getSupplierId().compareTo(-1L) == 0) ? null : position.getSupplierId(),
                StringCommon.isNullOrBlank(position.getName()) ? null : StringCommon.addLikeRightAndLeft(position.getName().trim()),
                position.getStatus()
        );
        if (!ArrayListCommon.isNullOrEmpty(list)) {
            for (Position p : list) {
                if (p.getSupplierId() != null) {
                    Param param = cacheService.getParamFromCache(Constant.KEY.USER_TYPE.toUpperCase(), p.getUserType() + "");
                    if (param != null) p.setUserTypeName(param.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<Long> getListGroupRoleId(Position position) throws Exception {
        ValidateCommon.validateNullObject(position.getSupplierId(), "supplierId");
        ValidateCommon.validateNullObject(position.getUserType(), "userType");
        return positionGroupRoleRepository.getListGroupRoleIdBySupplierIdAndUserType(position.getSupplierId(), position.getUserType());
    }

    @Override
    public PositionGroupRole createPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception {
        return savePositionGroupRole(positionGroupRole);
    }

    @Override
    public PositionGroupRole updatePositionGroupRole(PositionGroupRole positionGroupRole) throws Exception {
        PositionGroupRole positionGroupRoleOld = positionGroupRoleRepository.findById(positionGroupRole.getId()).orElse(null);
        if (positionGroupRoleOld == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        return savePositionGroupRole(positionGroupRoleOld.coppyFrom(positionGroupRole));
    }

    @Override
    public PositionGroupRole updateStatusPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception {
        ValidateCommon.validateNullObject(positionGroupRole.getSupplierId(), "supplierId");
        ValidateCommon.validateNullObject(positionGroupRole.getPositionId(), "positionId");
        ValidateCommon.validateNullObject(positionGroupRole.getGroupRoleId(), "groupRoleId");
        ValidateCommon.validateNullObject(positionGroupRole.getStatus(), "status");
        PositionGroupRole positionGroupRoleOld = positionGroupRoleRepository.findFirstBySupplierIdAndPositionIdAndGroupRoleIdAndStatus(
                positionGroupRole.getSupplierId(),
                positionGroupRole.getPositionId(),
                positionGroupRole.getGroupRoleId(),
                positionGroupRole.getStatus()
        );
        if (positionGroupRoleOld == null)
            return createPositionGroupRole(positionGroupRole);
        positionGroupRoleOld.setStatus(positionGroupRole.getStatus());
        positionGroupRoleOld.setUpdatedBy(cacheService.getUsernameFromHeader());
        return positionGroupRoleRepository.save(positionGroupRoleOld);
    }

    @Override
    public List<PositionGroupRole> getListPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception {
        return positionGroupRoleRepository.getList(
                positionGroupRole.getSupplierId(),
                positionGroupRole.getPositionId(),
                positionGroupRole.getGroupRoleId(),
                positionGroupRole.getStatus()
        );
    }

    @Override
    public List<GroupRole> getListGroupRoleByPosition(PositionGroupRole positionGroupRole) throws Exception {
        ValidateCommon.validateNullObject(positionGroupRole.getSupplierId(), "supplierId");
        ValidateCommon.validateNullObject(positionGroupRole.getPositionId(), "positionId");
//        Supplier supplier = supplierRepository.findById(positionGroupRole.getSupplierId()).orElse(null);
//        if (supplier == null || supplier.getStatus() == null || supplier.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
//            throw new CommonException(Response.DATA_INVALID, "Nhà cung cấp không tồn tại hoặc không hoạt động");
//        Position position = positionRepository.findById(positionGroupRole.getPositionId()).orElse(null);
//        if (position == null || position.getStatus() == null || position.getStatus() != Constant.STATUS_OBJECT.ACTIVE)
//            throw new CommonException(Response.DATA_INVALID, "Chức vụ không tồn tại hoặc không hoạt động");
        List<GroupRole> groupRoleList = new ArrayList<>();
//        ResponseBody responseBody = authServiceFeignAPI.getListGroupRole(new GroupRole(supplier.getModuleName(), Constant.STATUS_OBJECT.ACTIVE));
//        if (!responseBody.getResponseCode().equals(Response.SUCCESS.getResponseCode()))
//            throw new CommonException(responseBody.getResponseCode(), responseBody.getResponseMessage());
//        if (responseBody.getResponseData() != null)
//            groupRoleList = (new ObjectMapper()).convertValue(responseBody.getResponseData(), new TypeReference<List<GroupRole>>() {
//            });
//        if (!ArrayListCommon.isNullOrEmpty(groupRoleList))
//            groupRoleList.stream().forEach(x -> {
//                x.setStatus(positionGroupRoleRepository.existsBySupplierIdAndPositionIdAndGroupRoleIdAndStatus(supplier.getId(), position.getId(), x.getId(), Constant.STATUS_OBJECT.ACTIVE) ? Constant.STATUS_OBJECT.ACTIVE : Constant.STATUS_OBJECT.INACTIVE);
//            });
        return groupRoleList;
    }
}
