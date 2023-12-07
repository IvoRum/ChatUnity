package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.repository.UserRepository;
import com.tu.varna.chat.service.AuthService;
import com.tu.varna.chat.service.impl.exception.ServiceException;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {
    private static final UserRepository userRepository;

    static {
        userRepository = new UserRepository();
    }

    @Override public boolean logInUser(UserCredentials userCredentials) throws ServiceException {
        try {
            return userRepository.isUser(userCredentials);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override public boolean registerUser(UserCredentials userCredentials) {

        return false;
    }
}
