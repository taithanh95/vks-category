package com.bitsco.vks.category.service;

import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.repository.BankRepository;
import com.bitsco.vks.category.entities.Bank;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.constant.MessageContent;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.MessageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankServiceImpl implements BankService {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.SERVICE);

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CacheService cacheService;

    @Override
    public List<Bank> getListBank(Bank bank) throws Exception {
        return bankRepository.getList(
                StringCommon.isNullOrBlank(bank.getCode()) ? null : bank.getCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(bank.getName()) ? null : ("%" + bank.getName() + "%"),
                bank.getStatus()
        );
    }

    @Override
    public Bank updateBankStatus(long id, int status) throws Exception {
        Bank bank = bankRepository.findById(id).orElse(null);
        if (bank == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, MessageCommon.getMessage(MessageContent.OBJECT_NOT_FOUND_BY_FIELD_VALUE,
                    new String[]{"Bank", "id", id + ""}));
        if (bank.getStatus() == 0)
            bank.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        else
            bank.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        bank.setUpdatedBy(cacheService.getUsernameFromHeader());
        return bankRepository.save(bank);
    }

    @Override
    public Bank createOrUpdateBank(Bank bank) throws Exception {
        ValidateCommon.validateNullObject(bank.getCode(), "code");
        Bank provinceOld = bankRepository.findFirstByCode(bank.getCode());
        if (provinceOld == null) {
            ValidateCommon.validateNullObject(bank.getName(), "name");
            bank.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return saveBank(bank);
        } else return saveBank(provinceOld.coppyFrom(bank));
    }

    private Bank saveBank(Bank bank) throws Exception {
        if (bank.getId() != null) bank.setUpdatedBy(cacheService.getUsernameFromHeader());
        else bank.setCreatedBy(cacheService.getUsernameFromHeader());
        return bankRepository.save(bank);
    }
}
