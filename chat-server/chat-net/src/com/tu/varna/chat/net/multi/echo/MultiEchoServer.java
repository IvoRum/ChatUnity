package com.tu.varna.chat.net.multi.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiEchoServer {

    private static ServerSocket serverSocket;
    private static final int PORT=1300;

    public static void main(String[] args) throws IOException {

        try {
            System.out.println("\n Creating socket \n");
            serverSocket=new ServerSocket(PORT);
            System.out.println("\n Socket Created \n");
        } catch (IOException e) {

            throw new RuntimeException(e+"Socket did not open on port 1300");
        }
        do{
            Socket clientSocket=serverSocket.accept();
            System.out.println("\n New client Accepted! \n");

            Thread handler = new ClientHandler(clientSocket);
            handler.start();
        }while (true);
    }
}
