package com.bank.account.service.controller;

import com.bank.account.service.entity.Account;
import com.bank.account.service.exception.AccountServiceException;
import com.bank.account.service.model.AccountCreateRequest;
import com.bank.account.service.service.AccountService;
import com.bank.common.lib.model.response.CommonSuccessResponse;
import com.bank.common.lib.utils.MetadataContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bank.common.lib.utils.Constants;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/bank/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("account service running up..");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CommonSuccessResponse<Account>> createNewAccount(@RequestBody AccountCreateRequest accountCreateRequest){
        Account createdAccount = accountService.createAccount(accountCreateRequest);
        return getSpecificResponse("Account created successfully", Constants.CREATED_STATUS_CODE, createdAccount);
    }

    private <T> ResponseEntity<CommonSuccessResponse<T>> getSpecificResponse(String msg, String statusCode, T payload) {
        try {
            CommonSuccessResponse<T> response = CommonSuccessResponse.<T>builder()
                    .timestamp(String.valueOf(LocalDateTime.now()))
                    .status(Constants.SUCCESS_TAG)
                    .statusCode(statusCode)
                    .message(msg)
                    .metadata(MetadataContext.getMetadata())
                    .payload(payload)
                    .build();
            return ResponseEntity.status(Integer.parseInt(statusCode)).body(response);
        } catch (Exception e) {
            throw new AccountServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }
}
