package com.bank.account.service.service;

import com.bank.account.service.entity.Account;
import com.bank.account.service.model.AccountCreateRequest;

public interface AccountService {
    Account createAccount(AccountCreateRequest accountCreateRequest);
}
