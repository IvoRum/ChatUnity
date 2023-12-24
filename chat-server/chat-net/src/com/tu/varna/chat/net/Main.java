package com.tu.varna.chat.net;

import com.tu.varna.chat.net.auth.AuthNet;
import com.tu.varna.chat.net.chat.ChatNet;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private Socket socket;
    public static void main(String[] args) throws IOException {
        AuthNet authNet=new AuthNet();
        ChatNet chatNet=new ChatNet();
        authNet.start();
        System.out.println("Auth is running");
        chatNet.start();
        System.out.println("Chat is running");
    }
}
