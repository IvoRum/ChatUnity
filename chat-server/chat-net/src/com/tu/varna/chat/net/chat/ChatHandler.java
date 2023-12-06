package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.impl.ChatServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatHandler extends Thread {
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private static final ChatService chatService;

    static {
        chatService = new ChatServiceImpl();
    }

    public ChatHandler(Socket socket) {
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
        output.println("ECHO: " + chatService.pickMessage());

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
