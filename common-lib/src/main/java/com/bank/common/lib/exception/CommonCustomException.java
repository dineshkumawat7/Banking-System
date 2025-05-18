package com.bank.common.lib.exception;

import lombok.Getter;

@Getter
public class CommonCustomException extends RuntimeException{
    private final String msgCode;
    private final String exceptionMessage;

    public CommonCustomException(String msgCode, String exceptionMessage){
        super(exceptionMessage);
        this.msgCode = msgCode;
        this.exceptionMessage = exceptionMessage;
    }
}
