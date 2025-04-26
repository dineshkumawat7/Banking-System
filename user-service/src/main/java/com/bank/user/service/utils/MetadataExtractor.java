package com.bank.user.service.utils;

import com.bank.user.service.config.EnvironmentParamConfig;
import com.bank.user.service.model.common.Metadata;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public class MetadataExtractor {

    public static Metadata extract(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-Id");
        String correlationId = request.getHeader("X-Correlation-Id");
        String authenticatedUserId = "anonymous";
        String sourceIp = request.getHeader("X-Forwarded-For");

        Metadata metadata = Metadata.builder()
                .requestId(requestId != null ? requestId : UUID.randomUUID().toString())
                .correlationId(correlationId != null ? correlationId : UUID.randomUUID().toString())
                .authenticatedUserId(authenticatedUserId)
                .sourceIp(sourceIp != null ? sourceIp : request.getRemoteAddr())
                .environment(EnvironmentParamConfig.ENVIRONMENT)
                .serviceName(EnvironmentParamConfig.SERVICE_NAME)
                .build();
        return metadata;
    }
}
