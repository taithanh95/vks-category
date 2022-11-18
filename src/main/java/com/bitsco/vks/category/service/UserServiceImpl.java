package com.bitsco.vks.category.service;

import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.Param;
import com.bitsco.vks.category.entities.User;
import com.bitsco.vks.category.repository.PositionRepository;
import com.bitsco.vks.category.repository.UserRepository;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CacheService cacheService;
    @Autowired
    PositionRepository positionRepository;

    @Override
    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public User findById(User user) throws Exception {
        ValidateCommon.validateNullObject(user.getId(), "id");
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Override
    public User findFirstByUsername(User user) throws Exception {
        ValidateCommon.validateNullObject(user.getUsername(), "username");
        user.setUsername(user.getUsername().trim().toLowerCase());
        User rs = cacheService.getUserFromCache(user.getUsername());
        if (rs == null)
            return userRepository.findFirstByUsername(user.getUsername());
        else return rs;
    }

    @Override
    public PageResponse getPage(PageRequest pageRequest) throws Exception {
        User user = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), User.class);
        return PageCommon.toPageResponse(
                userRepository.getList(
                        StringCommon.isNullOrBlank(user.getUsername()) ? null : StringCommon.addLikeRightAndLeft(user.getUsername()).toLowerCase(),
                        StringCommon.isNullOrBlank(user.getName()) ? null : StringCommon.addLikeRightAndLeft(user.getName()).toLowerCase(),
                        StringCommon.isNullOrBlank(user.getAddress()) ? null : StringCommon.addLikeRightAndLeft(user.getAddress()).toLowerCase(),
                        StringCommon.isNullOrBlank(user.getEmail()) ? null : StringCommon.addLikeRightAndLeft(user.getEmail()).toLowerCase(),
                        StringCommon.isNullOrBlank(user.getPhone()) ? null : StringCommon.addLikeRightAndLeft(user.getPhone()).toLowerCase(),
                        user.getType(),
                        user.getStatus()
                ),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize()
        );
    }

    @Override
    public List<User> getList(User user) throws Exception {
        List<User> userList = userRepository.getList(
                StringCommon.isNullOrBlank(user.getUsername()) ? null : StringCommon.addLikeRightAndLeft(user.getUsername()).toLowerCase(),
                StringCommon.isNullOrBlank(user.getName()) ? null : StringCommon.addLikeRightAndLeft(user.getName()).toLowerCase(),
                StringCommon.isNullOrBlank(user.getAddress()) ? null : StringCommon.addLikeRightAndLeft(user.getAddress()).toLowerCase(),
                StringCommon.isNullOrBlank(user.getEmail()) ? null : StringCommon.addLikeRightAndLeft(user.getEmail()).toLowerCase(),
                StringCommon.isNullOrBlank(user.getPhone()) ? null : StringCommon.addLikeRightAndLeft(user.getPhone()).toLowerCase(),
                user.getType(),
                user.getStatus()
        );
        if (ArrayListCommon.isNullOrEmpty(userList))
            throw new CommonException(Response.DATA_NOT_FOUND);
        userList.stream().forEach(x -> {
            if (x.getType() != null && x.getType() > 0) {
                Param param = cacheService.getParamFromCache(Constant.KEY.USER_TYPE.toUpperCase(), x.getType() + "");
                x.setTypeName(param == null ? "" : param.getName());
            }
        });
        return userList;
    }
}
