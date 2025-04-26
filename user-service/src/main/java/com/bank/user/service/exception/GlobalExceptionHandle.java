package com.bank.user.service.exception;

import com.bank.user.service.model.common.ErrorResponse;
import com.bank.user.service.model.common.FieldErrorDetail;
import com.bank.user.service.model.common.FieldValidationErrorResponse;
import com.bank.user.service.utils.Constants;
import com.bank.user.service.utils.MetadataContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(Constants.FAILURE_TAG)
                .errorCode(Constants.UNSUPPORTED_MEDIA_TYPE_STATUS_CODE)
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceExceptionException(UserServiceException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(Constants.FAILURE_TAG)
                .errorCode(e.getMsgCode())
                .errorMessage(e.getMessage())
                .metadata(MetadataContext.getMetadata())
                .build();
        return ResponseEntity.status(Integer.parseInt(e.getMsgCode())).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldErrorDetail> fieldErrors = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            FieldErrorDetail fieldError = FieldErrorDetail.builder()
                    .field(((FieldError) error).getField())
                    .message(error.getDefaultMessage())
                    .code(error.getCode())
                    .rejectedValue((String) ((FieldError) error).getRejectedValue())
                    .build();
            fieldErrors.add(fieldError);
        });
        FieldValidationErrorResponse response = FieldValidationErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(Constants.FAILURE_TAG)
                .errorCode(Constants.BAD_REQUEST_STATUS_CODE)
                .errorMessage("Validation error")
                .fieldErrors(fieldErrors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(Constants.FAILURE_TAG)
                .errorCode(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE)
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
