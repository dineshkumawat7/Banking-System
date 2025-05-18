package com.bank.common.lib.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonEnvironmentParamConfig {
    @Value("${commonlib.service.environment}")
    private String environment;

    @Value("${commonlib.service.name}")
    private String serviceName;

    public static String ENVIRONMENT;
    public static String SERVICE_NAME;

    @PostConstruct
    private void init() {
        ENVIRONMENT = environment;
        SERVICE_NAME = serviceName;
    }
}
