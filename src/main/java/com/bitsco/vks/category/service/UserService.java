package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.User;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;

import java.util.List;

public interface UserService {
    List<User> findAll() throws Exception;

    User findById(User user) throws Exception;

    User findFirstByUsername(User user) throws Exception;

    PageResponse getPage(PageRequest pageRequest) throws Exception;

    List<User> getList(User user) throws Exception;
}
