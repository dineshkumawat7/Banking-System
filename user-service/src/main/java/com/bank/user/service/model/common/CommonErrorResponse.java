package com.bank.user.service.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonErrorResponse {
    private String timestamp;
    private String status;
    private String errorCode;
    private String errorMessage;
    private Metadata metadata;
}
