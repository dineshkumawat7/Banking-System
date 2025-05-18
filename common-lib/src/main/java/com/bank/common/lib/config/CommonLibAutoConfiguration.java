package com.bank.common.lib.config;

import com.bank.common.lib.filter.MetadataFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Collections;

@Configuration
public class CommonLibAutoConfiguration {
    @Bean
    public FilterRegistrationBean<MetadataFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<MetadataFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new MetadataFilter());
        filterFilterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterFilterRegistrationBean;
    }
}
