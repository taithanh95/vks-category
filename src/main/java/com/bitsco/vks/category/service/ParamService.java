package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.Param;

import java.util.List;

public interface ParamService {

    Param createOrUpdate(Param param) throws Exception;

    Param findFirstByGroupAndCode(Param param) throws Exception;

    List<Param> findByGroup(Param param) throws Exception;

    List<Param> getListParam(Param param) throws Exception;

}
