package com.bank.common.lib.exception;

import com.bank.common.lib.model.response.CommonErrorResponse;
import com.bank.common.lib.utils.Constants;
import com.bank.common.lib.utils.MetadataContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommonCustomException.class)
    public ResponseEntity<CommonErrorResponse> handleUserServiceExceptionException(CommonCustomException e) {
        CommonErrorResponse commonErrorResponse = CommonErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(Constants.FAILURE_TAG)
                .errorCode(e.getMsgCode())
                .errorMessage(e.getMessage())
                .metadata(MetadataContext.getMetadata())
                .build();
        return ResponseEntity.status(Integer.parseInt(e.getMsgCode())).body(commonErrorResponse);
    }
}
