package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.UnreadMessage;

import java.net.Socket;
import java.util.List;

public class GetUnreadMessages extends BaseChatAction{
    public GetUnreadMessages(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override public void run() {
        getAllGroups();
    }

    private void getAllGroups() {
        String[] inputPackage = received.split("urm:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        for (int i = 1; i < packageParts.length; i++) {
            List<UnreadMessage> frindInfo = chatService.getUnreadMessage(Integer.parseInt(packageParts[i]));
            System.out.println(frindInfo);
            output.println(frindInfo);
        }
    }
}
