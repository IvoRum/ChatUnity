package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.repository.UserRepository;
import com.tu.varna.chat.service.AuthService;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {
    private final static UserRepository userRepository;

    static {
        userRepository=new UserRepository();
    }
    @Override public boolean logInUser(UserCredentials userCredentials) {
        try {
            userRepository.isUser(userCredentials);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override public boolean registerUser(UserCredentials userCredentials) {

        return false;
    }
}
