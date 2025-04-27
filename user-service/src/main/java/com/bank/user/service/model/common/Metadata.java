package com.bank.user.service.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metadata {
    private String requestId;
    private String correlationId;
    private String authenticatedUserId;
    private String sourceIp;
    private String environment;
    private String serviceName;
}
