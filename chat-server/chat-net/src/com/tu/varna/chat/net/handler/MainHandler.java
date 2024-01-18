package com.tu.varna.chat.net.handler;

import com.tu.varna.chat.net.auth.LogIn;
import com.tu.varna.chat.net.chat.*;
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

public class MainHandler extends Thread {
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
                        Thread getMs = new GetMessage(clientSocket, received);
                        getMs.start();
                        break;
                    case RMS:
                        Thread rMs = new ReloadMessage(clientSocket, received);
                        rMs.start();
                        break;
                    case URM:
                        Thread getUnreadMs = new GetUnreadMessages(clientSocket, received);
                        getUnreadMs.start();
                        break;
                    case SMS:
                        Thread sendMs = new SendMessage(clientSocket, received);
                        sendMs.start();
                        break;
                    case GFR:
                        Thread getFr = new GetFriend(clientSocket, received);
                        getFr.start();
                        break;
                    case FRI:
                        break;
                    case GUG:
                        Thread getGr = new GetGroup(clientSocket, received);
                        getGr.start();
                        break;
                    case LOG:
                        Thread logIn = new LogIn(clientSocket, received);
                        logIn.start();
                        break;
                    case REG:
                        break;
                }
            } while (!received.equals("QUIT"));
        } catch (
                NoSuchElementException e) {
            // Client disconnected, handle accordingly
            System.out.println("Client disconnected");
        }finally {
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
