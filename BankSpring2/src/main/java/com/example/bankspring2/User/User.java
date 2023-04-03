package com.example.bankspring2.User;

import com.example.bankspring2.Enum.Roles;
import com.example.bankspring2.Function.Function;
import com.example.bankspring2.Transaction.Transaction;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private int countNumber;
    private double accountBalance;
    private String pin;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    List<Roles> roles;
    @OneToMany
    List<Transaction> transactions;
    private boolean status;
    public User(String email, String password, String firstName, String lastName, int pin, List<Roles> roles) {
        this.email = email;
        this.password = Function.hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = Function.hashPin(pin);
        this.login = Function.generateRandomString();
        this.countNumber = Function.generateRandomNumber();
        this.roles = roles;
        this.status = true;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
