package com.tu.varna.chat.net.practice.thred;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TelnetServer {
    public static void main(String[] args) //throws Exception
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("wainting for clients...");
            boolean stop = false;
            //while(!stop)

            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello  client!");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientInput = input.readLine();
            System.out.println(clientInput);
            input.close();
            out.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
