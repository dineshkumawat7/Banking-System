package com.bank.user.service.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EnvironmentParamConfig {

    @Value("${service.environment}")
    private String environment;

    @Value("${service.name}")
    private String serviceName;


    public static String ENVIRONMENT;
    public static String SERVICE_NAME;

    @PostConstruct
    private void init() {
        ENVIRONMENT = environment;
        SERVICE_NAME = serviceName;
    }
}
