package com.bank.common.lib.model;

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
