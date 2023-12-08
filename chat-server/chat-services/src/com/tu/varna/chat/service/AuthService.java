package com.tu.varna.chat.service;

import com.tu.varna.chat.common.net.NewUserCredentials;
import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.service.impl.exception.ServiceException;

import java.sql.SQLException;

public interface AuthService {
    boolean logInUser(UserCredentials userCredentials) throws SQLException, ServiceException;
    int registerUser(NewUserCredentials newUserCredentials) throws ServiceException;
}
