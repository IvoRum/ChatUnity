package com.tu.varna.chat.net.handler;

import com.tu.varna.chat.net.chat.GetMessage;
import com.tu.varna.chat.net.chat.SendMessage;
import com.tu.varna.chat.net.chat.archiv.ChatHandler;
import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.UserService;
import com.tu.varna.chat.service.impl.ChatServiceImpl;
import com.tu.varna.chat.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainHandler extends Thread{
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;

    private static final ChatService chatService;
    private static final UserService userService;

    static {
        userService = new UserServiceImpl();
        chatService = new ChatServiceImpl();
    }

    public MainHandler(Socket socket) {
        this.clientSocket = socket;

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
        try {
            do {

                received = input.nextLine();
                assert received == null : "Pack must not be null";
                String firstThreeCharacters = received.substring(0, 3);
                Prefix prefix;
                try {
                    prefix = Prefix.getInstance(firstThreeCharacters);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                switch (prefix) {
                    case GMS:
                        Thread getMs = new GetMessage(clientSocket,received);
                        getMs.start();
                        //getAllConversationsForAgivenUser(received);
                        break;
                    case SMS:
                        Thread sendMs = new SendMessage(clientSocket,received);
                        sendMs.start();
                        //sendMessage(received);
                        break;
                    case GFR:
                        //getAllFriendsList(received);
                        break;
                    case FRI:
                        break;
                    case GUG:
                        //getAllGroups(received);
                }
            } while (!received.equals("QUIT"));
        } catch (
                NoSuchElementException e) {
            // Client disconnected, handle accordingly
            System.out.println("Client disconnected");
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    System.out.println("Closing down connection");
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
            }
        }
        //add ip to blacklist
    }
}
