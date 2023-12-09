package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.net.ChatReachedPoint;
import com.tu.varna.chat.common.net.NewUserCredentials;
import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.common.net.UserNames;
import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.impl.ChatServiceImpl;
import com.tu.varna.chat.service.impl.exception.ServiceException;

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
        String received = "";
        received = input.nextLine();
        //TODO add to black list if package dose not start whit 'log:' or 'reg:'
        assert received == null : "Pack must not be null";
        assert received == "" : "Package must not be empty";
        String firstThreeCharacters = received.substring(0, 3);
        if (firstThreeCharacters.equals("gms")) {
            getAllConversationsForAgivenUser(received);
        } else if ("sms".equals(firstThreeCharacters)) {
            sendMessage(received);
        } else {
            //add ip to blacklist
        }
    }

    private void getAllConversationsForAgivenUser(String received) {
        String[] inputPackage = received.split("gms:");

        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 2; i < packageParts.length; i++) {
            String[] chatReachedPointParts = packageParts[i].split("@");
            System.out.println(chatService.receiveMessage(
                    new ChatReachedPoint(Integer.parseInt(chatReachedPointParts[0]), Integer.parseInt(chatReachedPointParts[1]))));
        }
    }

    private void sendMessage(String received) {
    }

    private void testConnection() {
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
