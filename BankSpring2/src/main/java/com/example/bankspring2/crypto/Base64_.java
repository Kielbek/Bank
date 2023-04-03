package com.example.bankspring2.crypto;

import java.io.UnsupportedEncodingException;

public interface Base64_ {
    String encode(byte[] bytes);

    byte[] decode(String message) throws UnsupportedEncodingException;
}
