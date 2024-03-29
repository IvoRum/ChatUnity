package com.tu.varna.chat.net.chat.archiv;

import com.tu.varna.chat.common.dto.GroupDto;
import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.common.net.ChatReachedPoint;
import com.tu.varna.chat.net.handler.Prefix;
import com.tu.varna.chat.service.ChatService;
import com.tu.varna.chat.service.UserService;
import com.tu.varna.chat.service.impl.ChatServiceImpl;
import com.tu.varna.chat.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class
ChatHandler extends Thread {
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private static final ChatService chatService;
    private static final UserService userService;

    static {
        userService = new UserServiceImpl();
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
                        getAllConversationsForAgivenUser(received);
                        break;
                    case SMS:
                        sendMessage(received);
                        break;
                    case GFR:
                        getAllFriendsList(received);
                        break;
                    case FRI:
                        break;
                    case GUG:
                        getAllGroups(received);
                }
            } while (!received.equals("QUIT"));
        } catch (
                NoSuchElementException e) {
            // Client disconnected, handle accordingly
            System.out.println("Client disconnected");
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    System.out.println("Closing down connection");
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
            }
        }
        //add ip to blacklist
    }

    private void getAllGroups(String received) {
        String[] inputPackage = received.split("gug:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            Set<GroupDto> frindInfo = userService.getAllGroups(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }

    private void getAllFriendsList(String received) {
        String[] inputPackage = received.split("gfr:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            Set<UserHandleDto> frindInfo = userService.allFriends(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }

    /**
     * Calls repository to return all the specified conversation messages.
     * gms: userId conversation$order .....
     * EX
     * gms: 1 1@3 1@2
     *
     * @param received
     */
    public void getAllConversationsForAgivenUser(String received) {
        String[] inputPackage = received.split("gms:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        List<List<MessageReachedPointDto>> allNewMessagesList = new ArrayList<>();
        for (int i = 2; i < packageParts.length; i++) {
            String[] chatReachedPointParts = packageParts[i].split("@");
            List<MessageReachedPointDto> allNewMessages = chatService.receiveMessage(
                    new ChatReachedPoint(Integer.parseInt(chatReachedPointParts[0]), Integer.parseInt(chatReachedPointParts[1])));
            allNewMessagesList.add(allNewMessages);
        }
        System.out.println(allNewMessagesList);
        output.println(allNewMessagesList);
    }

    /**
     * The order of a 'sms' is as follows:
     * sms: userId converstion order $content
     * EX
     * sms: 1 1 43 $Hello Deme
     */
    private void sendMessage(String received) {
        /*
        String[] inputPackage = received.split("sms:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 2; i < packageParts.length; i++) {
         */
        String[] inputPackage = received.split("sms:");
        String[] packagePartsContent = inputPackage[1].split("\\$");
        String[] packageParts = packagePartsContent[0].split("\\s+");

        // save to db
        chatService.sendMessage(Integer.parseInt(packageParts[1]), Integer.parseInt(packageParts[2]), Integer.parseInt(packageParts[3]), packagePartsContent[1]);
        // return message
        output.println(">" + inputPackage[1]);
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
