package com.example.bankspring2.filters;

import com.example.bankspring2.Enum.Roles;
import org.springframework.stereotype.Component;

@Component
public class CheckUserRoleFilter extends BaseRoleFilter {

    public CheckUserRoleFilter(CurrentUserHolder currentUserHolder) {
        super(Roles.USER, currentUserHolder);
    }
}
