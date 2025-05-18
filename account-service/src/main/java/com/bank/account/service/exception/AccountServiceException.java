package com.bank.account.service.exception;

import lombok.Getter;

@Getter
public class AccountServiceException extends RuntimeException{
    private final String msgCode;
    private final String exceptionMessage;

    public AccountServiceException(String msgCode, String exceptionMessage){
        super(exceptionMessage);
        this.msgCode = msgCode;
        this.exceptionMessage = exceptionMessage;
    }
}
