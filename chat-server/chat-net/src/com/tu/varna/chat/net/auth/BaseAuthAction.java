package com.tu.varna.chat.net.auth;

import com.tu.varna.chat.service.AuthService;
import com.tu.varna.chat.service.impl.AuthServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class BaseAuthAction extends Thread{

    String received;
    Socket socket;
    private Scanner input;
    PrintWriter output;

    static final AuthService authService;

    static {
        authService = new AuthServiceImpl();
    }

    public BaseAuthAction(java.net.Socket socket, final String received) {
        this.socket = socket;
        this.received=received;
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
