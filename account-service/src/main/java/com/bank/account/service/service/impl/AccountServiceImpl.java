package com.bank.account.service.service.impl;

import com.bank.account.service.entity.Account;
import com.bank.account.service.model.AccountCreateRequest;
import com.bank.account.service.repository.AccountRepo;
import com.bank.account.service.service.AccountService;
import com.bank.account.service.service.UserServiceClient;
import com.bank.account.service.utils.AccountNumberGenerator;
import com.bank.common.lib.exception.CommonCustomException;
import com.bank.common.lib.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Account createAccount(AccountCreateRequest accountCreateRequest) {
        Object user = userServiceClient.getUserById(accountCreateRequest.getUserId());
        System.out.println(user.toString());
        if(user == null){
            throw new CommonCustomException(Constants.NOT_FOUND_STATUS_CODE, String.format("User not found with id: %d", accountCreateRequest.getUserId()));
        }
        Account account = Account.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .accountType(accountCreateRequest.getAccountType())
                .accountHolderName(accountCreateRequest.getAccountHolderName())
                .userId(accountCreateRequest.getUserId())
                .amount(accountCreateRequest.getAmount())
                .createdAt(String.valueOf(LocalDateTime.now()))
                .updateBy(String.valueOf(LocalDateTime.now()))
                .createdBy("user")
                .updatedAt("user")
                .isActive(true)
                .build();
        return accountRepo.save(account);
    }
}
