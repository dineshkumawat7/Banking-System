package com.bank.user.service.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldErrorDetail {
    private String field;
    private String rejectedValue;
    private String code;
    private String message;
}
