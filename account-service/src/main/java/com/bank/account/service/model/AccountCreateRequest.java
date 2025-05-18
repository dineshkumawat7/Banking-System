package com.bank.account.service.model;

import com.bank.account.service.enums.AccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateRequest {
    private String userId;
    private String accountHolderName;
    private AccountType accountType;
    private Double amount;
}
