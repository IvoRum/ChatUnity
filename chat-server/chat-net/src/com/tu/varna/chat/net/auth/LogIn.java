package com.tu.varna.chat.net.auth;

import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.service.impl.exception.ServiceException;

import java.net.Socket;
import java.sql.SQLException;

public class LogIn extends BaseAuthAction{

    public LogIn(final Socket socket, final String received) {
        super(socket,received);
    }

    @Override public void run() {
        logInUser();
    }
    private void logInUser() {
        String[] inputPackage = received.split("log:");
        String[] packageParts = inputPackage[1].split("\\s+");
        try {
            var user=authService.logInUser(new UserCredentials(packageParts[1], packageParts[2]));
            output.println(user);
            System.out.println("User LogedIn: "+user);
        } catch (SQLException e) {
            output.println("User not found");
        } catch (ServiceException e) {
            //throw new RuntimeException(e);
        }
    }
}
