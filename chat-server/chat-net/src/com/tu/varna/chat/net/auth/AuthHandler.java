package com.tu.varna.chat.net.auth;

import com.tu.varna.chat.service.AuthService;
import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.impl.AuthServiceImpl;
import com.tu.varna.chat.service.impl.ChatServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AuthHandler extends Thread {

    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private static final AuthService chatService;

    static {
        chatService = new AuthServiceImpl();
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
        
        assert received==null:"Pack";
        
        //String[] inputPackage=received.split("log:");

        socket.getInetAddress();
        //String[] packageParts = inputPackage[0].split("\\s+");

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
}
