package com.example.bankspring2.User;

import com.example.bankspring2.Enum.Roles;

import java.util.List;

public class CurrentUser {
    private long id;
    private String email;
    private List<Roles> role;

    public CurrentUser(long id, String email, List<Roles> role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Roles> getRole() {
        return role;
    }

    public void setRole(List<Roles> role) {
        this.role = role;
    }
}
