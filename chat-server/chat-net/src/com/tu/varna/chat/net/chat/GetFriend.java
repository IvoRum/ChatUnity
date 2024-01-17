package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.UserHandleDto;

import java.net.Socket;
import java.util.Set;

public class GetFriend extends BaseChatAction
{
    public GetFriend(final Socket socket, final String received) {
        super(socket, received);
    }
    @Override
    public void run() {
        getAllFriendsList();
    }

    private void getAllFriendsList() {
        String[] inputPackage = received.split("gfr:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            Set<UserHandleDto> frindInfo = userService.allFriends(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }
}
