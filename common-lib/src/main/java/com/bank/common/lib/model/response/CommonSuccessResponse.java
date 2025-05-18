package com.bank.common.lib.model.response;

import lombok.*;
import com.bank.common.lib.model.Metadata;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonSuccessResponse<T>{
    private String timestamp;
    private String status;
    private String statusCode;
    private String message;
    private Metadata metadata;
    private T payload;
}
