package com.example.bankspring2.filters;

import com.example.bankspring2.Enum.Roles;
import com.example.bankspring2.User.CurrentUser;
import jakarta.servlet.*;

import java.io.IOException;

public class BaseRoleFilter implements Filter {

    Roles role;

    private CurrentUserHolder currentUserHolder;

    public BaseRoleFilter(Roles role, CurrentUserHolder currentUserHolder) {
        this.role = role;
        this.currentUserHolder = currentUserHolder;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CurrentUser currentUser = currentUserHolder.getCurrentUser();
        for (Roles role : currentUser.getRole()) {
            if (role.equals(this.role)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        throw new RuntimeException(role + " role not found!");
    }
}
