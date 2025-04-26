package com.bank.user.service.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse <T>{
    private String timestamp;
    private String status;
    private String statusCode;
    private String message;
    private Metadata metadata;
    private T payload;
}
