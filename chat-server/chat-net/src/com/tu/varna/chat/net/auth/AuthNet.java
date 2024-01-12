package com.tu.varna.chat.net.auth;

import com.tu.varna.chat.net.chat.ChatHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthNet extends Thread {

    private static ServerSocket serverSocket;
    private static final int PORT=1301;

    public void run(){
        try {
            serverSocket=new ServerSocket(PORT);
        } catch (IOException e) {

            throw new RuntimeException(e+"Chat Socket did not open on port 1300");
        }
        do{
            Socket clientSocket= null;
            try {
                System.out.println(serverSocket.getLocalSocketAddress());
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n New client Accepted! \n");

            Thread handler = new AuthHandler(clientSocket);
            handler.start();
        }while (true);
    }
}
