package com.example.bankspring2.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;

public class CryptoImpl implements Crypto {

    @Override
    public byte[] encrypt(String message, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(byte[] hash, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(hash);
        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }

    @Override
    public KeyPair createKeys(String password) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4);
        keyPairGen.initialize(spec, new SecureRandom(password.getBytes(StandardCharsets.UTF_8)));
        return keyPairGen.generateKeyPair();
    }
}
