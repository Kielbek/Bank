package com.example.bankspring2.crypto;

import java.util.Base64;

public class Base64Impl implements Base64_ {

    @Override
    public String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public byte[] decode(String message) {
        return Base64.getDecoder().decode(message);
    }
}
