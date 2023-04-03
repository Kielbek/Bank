package com.example.bankspring2.Function;

import com.example.bankspring2.User.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

public class Function {
    public static int generateRandomNumber() {
        Random random = new Random();
        int min = 100000000; // minimalna wartość
        int max = 999999999; // maksymalna wartość
        return random.nextInt((max - min) + 1) + min;
    }

    public static String generateRandomString() {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 7;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static String hashPin(int pin) {
        String pinString = Integer.toString(pin);
        String hashedPin = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(pinString.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedPin = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPin;
    }

    public static void checkAmount(int amount) {
        if (amount < 0) throw new RuntimeException("Bad amount!");
    }

    public static void checkAmount(User user, int amount) {
        if (amount < 0) throw new RuntimeException("Bad amount!");
        if (user.getAccountBalance() < amount) throw new RuntimeException("Brak srodkow!");

    }

    public static void checkAccountStatus(User user) {
        if (!user.getStatus()) throw new RuntimeException("Account is blocked!");
    }

    public static void checkOptionalIsEmpty(Optional<User> user) {
        if (user.isEmpty()) throw new RuntimeException("User not found!");
    }

}
