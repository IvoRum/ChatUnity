package com.tu.varna.chat.net.auth;

import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.service.AuthService;
import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.impl.AuthServiceImpl;
import com.tu.varna.chat.service.impl.ChatServiceImpl;
import com.tu.varna.chat.service.impl.exception.ServiceException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AuthHandler extends Thread {

    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private static final AuthService authService;

    static {
        authService = new AuthServiceImpl();
    }

    public AuthHandler(java.net.Socket socket) {
        this.socket = socket;

        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        testConnection();
    }

    private void testConnection(){
        String received = "";
        received = input.nextLine();
        assert received==null:"Pack must not be null";
        assert received=="":"Package must not be empty";
        String firstThreeCharacters = received.substring(0, 3);
        if(firstThreeCharacters.equals("log")){
            logInUser(received);
        }else if("reg".equals(firstThreeCharacters)) {
            registerUser(received);
        }else {
            //add ip to blacklist
        }
        //TODO add to black list if package dose not start whit 'log:' or 'reg:'

        output.println("ECHO: "+socket.getInetAddress());

        try {
            if (socket != null) {
                System.out.println("Closing down connection");
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Unable to disconnect!");
        }
    }

    private void registerUser(String received) {
    }

    private void logInUser(String received) {
        String[] inputPackage=received.split("log:");

        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        try {
            System.out.println(authService.logInUser(new UserCredentials(packageParts[1],packageParts[2])));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
