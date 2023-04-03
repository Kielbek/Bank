package com.example.bankspring2.User;

import com.example.bankspring2.Enum.Roles;

import java.util.List;

public class UserDTO {
    private long Id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int countNumber;
    private int pin;
    List<Roles> roles;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public int getPin() {
        return pin;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public long getId() {
        return Id;
    }
}
