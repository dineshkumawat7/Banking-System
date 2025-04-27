package com.bank.user.service.filter;

import com.bank.user.service.exception.UserServiceException;
import com.bank.user.service.model.common.Metadata;
import com.bank.user.service.utils.Constants;
import com.bank.user.service.utils.MetadataContext;
import com.bank.user.service.utils.MetadataExtractor;
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
        MetadataContext.setMetadata(MetadataExtractor.extract(request));
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        } finally {
            MetadataContext.clearMetadata();
        }
    }
}
