package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.common.dto.UserNotFriendDto;

import java.net.Socket;
import java.util.List;
import java.util.Set;

public class GetNotFriend extends BaseChatAction{
    public GetNotFriend(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override public void run() {
        getAllNonFriendsList();
    }
    private void getAllNonFriendsList() {
        String[] inputPackage = received.split("gnf:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            List<UserNotFriendDto> frindInfo = userService.getNotFriend(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }
}
