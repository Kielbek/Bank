package com.example.bankspring2.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public interface Crypto {
    byte[] encrypt(String message, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    String decrypt(byte[] hash, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    KeyPair createKeys(String password) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
}
