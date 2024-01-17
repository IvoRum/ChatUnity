package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.UserService;
import com.tu.varna.chat.service.impl.ChatServiceImpl;
import com.tu.varna.chat.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class BaseChatAction extends Thread {
    Socket socket;
    //Add if necessary
    //Scanner input;
    PrintWriter output;
    String received;

    static final ChatService chatService;
    static final UserService userService;

    static {
        userService = new UserServiceImpl();
        chatService = new ChatServiceImpl();
    }

    public BaseChatAction(Socket socket, String received) {
        this.socket = socket;
        this.received = received;

        try {
            //input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
