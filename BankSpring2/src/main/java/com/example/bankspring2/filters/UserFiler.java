package com.example.bankspring2.filters;


import com.example.bankspring2.User.CurrentUser;
import com.example.bankspring2.User.User;
import com.example.bankspring2.User.UserRepository;
import com.example.bankspring2.token.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserFiler implements Filter {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    private CurrentUserHolder currentUserHolder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//        tokenService.checkLoginData(token);
        User userDTO = null;
        try {
            userDTO = tokenService.getUser(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        currentUserHolder.setCurrentUser(new CurrentUser(userDTO.getId(), userDTO.getEmail(), userDTO.getRoles()));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
