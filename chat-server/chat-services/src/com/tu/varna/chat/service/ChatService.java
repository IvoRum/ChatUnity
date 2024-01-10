package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.ChatDuo;
import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import com.tu.varna.chat.common.dto.UserDto;
import com.tu.varna.chat.common.net.ChatReachedPoint;

import java.util.List;
import java.util.Set;

public interface ChatService {
    void createChat(int userSender,int userReceiver);
    void sendMessage(int userSender,int userReceiver,String content);
    List<MessageReachedPointDto> receiveMessage(ChatReachedPoint chatReachedPoint);
    void getChatrelation(int userSender,int userReceiver);
    ChatDuo getChatDuo();
    Set<UserDto> getChatsUsers();
    void addToChatGrup(int conversationId, int userId);
    String pickMessage();
}
