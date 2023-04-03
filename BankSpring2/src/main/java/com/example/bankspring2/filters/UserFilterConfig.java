package com.example.bankspring2.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFilterConfig {

    @Bean
    public FilterRegistrationBean<UserFiler> getUserFilter(UserFiler userFiler) {
        FilterRegistrationBean<UserFiler> registration = new FilterRegistrationBean<>();
        registration.setFilter(userFiler);
        registration.addUrlPatterns("/admin/*");
        registration.addUrlPatterns("/user/*");
        registration.setOrder(0);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CheckAdminRoleFilter> getCheckAdminRoleFilter(CheckAdminRoleFilter checkRoleFilter) {
        FilterRegistrationBean<CheckAdminRoleFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(checkRoleFilter);
        registration.addUrlPatterns("/admin/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CheckUserRoleFilter> getCheckUserRoleFilter(CheckUserRoleFilter checkUserRoleFilter) {
        FilterRegistrationBean<CheckUserRoleFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(checkUserRoleFilter);
        registration.addUrlPatterns("/user/*");
        return registration;
    }
}
