package com.bank.common.lib.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldValidationErrorResponse {
    private String timestamp;
    private String status;
    private String errorCode;
    private String errorMessage;
    private List<FieldErrorDetail> fieldErrors;
}
