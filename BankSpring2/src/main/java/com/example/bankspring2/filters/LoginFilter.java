package com.example.bankspring2.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        System.out.printf("Request received: %s %s%n", httpRequest.getMethod(), httpRequest.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
