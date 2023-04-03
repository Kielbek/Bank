package com.example.bankspring2.filters;

import com.example.bankspring2.Enum.Roles;
import org.springframework.stereotype.Component;


@Component
public class CheckAdminRoleFilter extends BaseRoleFilter {

    public CheckAdminRoleFilter(CurrentUserHolder currentUserHolder) {
        super(Roles.ADMIN, currentUserHolder);
    }
}
