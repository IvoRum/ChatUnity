package com.tu.varna.chat.service;

import com.tu.varna.chat.common.net.UserCredentials;

public interface AuthService {
    boolean logInUser(UserCredentials userCredentials);
    boolean registerUser(UserCredentials userCredentials);
}
