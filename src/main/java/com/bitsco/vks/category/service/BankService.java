package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.Bank;

import java.util.List;

public interface BankService {

    List<Bank> getListBank(Bank bank) throws Exception;

    Bank updateBankStatus(long id, int status) throws Exception;

    Bank createOrUpdateBank(Bank bank) throws Exception;
}
