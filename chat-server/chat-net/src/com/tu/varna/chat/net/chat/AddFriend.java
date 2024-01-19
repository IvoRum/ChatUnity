package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import com.tu.varna.chat.common.net.ChatReachedPoint;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AddFriend extends BaseChatAction{
    public AddFriend(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override public void run() {
        addFriend();
    }

    /**
     * Add a friend will add a friend in both directions
     * EX
     * afr: 9 11
     */
    private void addFriend() {
        String[] inputPackage = received.split("afr:");
        String[] packageParts = inputPackage[1].split("\\s+");
        // save to db
        userService.addFriend(Integer.parseInt(packageParts[1]), Integer.parseInt(packageParts[2]));
        // return message
        output.println("Friend added");
    }
}
