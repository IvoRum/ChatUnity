package com.tu.varna.chat.net.chat;

import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import com.tu.varna.chat.common.net.ChatReachedPoint;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GetMessage extends BaseChatAction {

    public GetMessage(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override
    public void run() {
        getAllConversationsForAgivenUser();
    }

    /**
     * Calls repository to return all the specified conversation messages.
     * gms: userId conversation$order .....
     * EX
     * gms: 1 1@3 1@2
     */
    public void getAllConversationsForAgivenUser() {
        String[] inputPackage = received.split("gms:");
        socket.getInetAddress();
        String[] packageParts = inputPackage[1].split("\\s+");
        List<List<MessageReachedPointDto>> allNewMessagesList = new ArrayList<>();
        for (int i = 2; i < packageParts.length; i++) {
            String[] chatReachedPointParts = packageParts[i].split("@");
            List<MessageReachedPointDto> allNewMessages = chatService.receiveMessage(
                    new ChatReachedPoint(Integer.parseInt(chatReachedPointParts[0]),
                            Integer.parseInt(chatReachedPointParts[1])));
            allNewMessagesList.add(allNewMessages);
        }
        System.out.println(allNewMessagesList);
        output.println(allNewMessagesList);
    }
}
