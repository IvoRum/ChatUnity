package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.GroupDto;

import java.net.Socket;
import java.util.Set;

public class GetGroup extends BaseChatAction{
    public GetGroup(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override
    public void run() {
        getAllGroups();
    }

    private void getAllGroups() {
        String[] inputPackage = received.split("gug:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            Set<GroupDto> frindInfo = userService.getAllGroups(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }
}
