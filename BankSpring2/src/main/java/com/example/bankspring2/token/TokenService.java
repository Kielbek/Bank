package com.example.bankspring2.token;

import com.example.bankspring2.User.User;
import com.example.bankspring2.User.UserRepository;
import com.example.bankspring2.crypto.Base64Impl;
import com.example.bankspring2.crypto.CryptoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class TokenService {
    Base64Impl base64 = new Base64Impl();
    CryptoImpl crypto = new CryptoImpl();

    KeyPair code;

    @Autowired
    ObjectMapper objectMapper;
    private static final int TIME_LIMIT_IN_SECONDS = 300;
    private final HashMap<String, TokenData> tokenMap = new HashMap<>();
    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createToken(Long userId) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        LocalDateTime loginData = LocalDateTime.now();
        TokenData tokenData = new TokenData(userId, loginData);

        String tokenDataJson = writeTokenDataAsJson(tokenData);
        code = crypto.createKeys("abc");
        byte[] encrypt = crypto.encrypt(tokenDataJson, code.getPublic());

        return base64.encode(encrypt);
    }

    public User getUser(String token) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, ClassNotFoundException {
        byte[] encryptedTokenData = base64.decode(token);
        String decrypt = crypto.decrypt(encryptedTokenData, code.getPrivate());

        TokenData tokenDataJson = readTokenDataFromJson(decrypt);

        Optional<User> user = userRepository.findById(tokenDataJson.getId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        return user.get();
    }

    public void checkLoginData(String token) {
        LocalDateTime actualDateTime = LocalDateTime.now();
        Duration duration = Duration.between(tokenMap.get(token).getTime(), actualDateTime);
        long durationInSeconds = Math.abs(duration.getSeconds());
        if (durationInSeconds > TIME_LIMIT_IN_SECONDS) {
            tokenMap.remove(token);
            throw new RuntimeException("Time has passed");
        }
    }

    TokenData readTokenDataFromJson(String decrypt) throws JsonProcessingException {
        return objectMapper.readValue(decrypt, TokenData.class);
    }

    String writeTokenDataAsJson(TokenData tokenData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tokenData);
    }

}
