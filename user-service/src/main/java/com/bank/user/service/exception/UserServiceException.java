package com.bank.user.service.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException{
    private final String msgCode;
    private final String exceptionMessage;

    public UserServiceException(String msgCode, String exceptionMessage){
        super(exceptionMessage);
        this.msgCode = msgCode;
        this.exceptionMessage = exceptionMessage;
    }
}
