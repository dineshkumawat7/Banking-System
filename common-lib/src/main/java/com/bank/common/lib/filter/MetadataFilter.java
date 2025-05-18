package com.bank.common.lib.filter;

import com.bank.common.lib.exception.CommonCustomException;
import com.bank.common.lib.utils.Constants;
import com.bank.common.lib.utils.MetadataContext;
import com.bank.common.lib.utils.RequestMetadataBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MetadataFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Store in ThreadLocal or context for access in controllers/services
        MetadataContext.setMetadata(RequestMetadataBuilder.extract(request));
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new CommonCustomException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        } finally {
            MetadataContext.clearMetadata();
        }
    }
}
