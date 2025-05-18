package com.bank.common.lib.model.response;

import lombok.*;
import com.bank.common.lib.model.Metadata;

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
