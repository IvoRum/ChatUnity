package com.tu.varna.chat.net;

import com.tu.varna.chat.net.multi.echo.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiChatServer {

    private static ServerSocket serverSocket;
    private static final int PORT=1300;

    public static void chatServer(String[] args) throws IOException {

        try {
            serverSocket=new ServerSocket(PORT);
            System.out.println(serverSocket.getInetAddress());
            System.out.println(serverSocket.getLocalSocketAddress());
        } catch (IOException e) {

            throw new RuntimeException(e+"Chat Socket did not open on port 1300");
        }
        do{
            Socket clientSocket=serverSocket.accept();
            System.out.println("\n New client Accepted! \n");

            Thread handler = new ClientHandler(clientSocket);
            handler.start();
        }while (!serverSocket.isClosed());
    }
    /*To access multible sockets use
         for (;;) {
            SocketHandler socketHander = new SocketHandler(serverSocket.accept());
            socketHander.start();
        }
    */


}
