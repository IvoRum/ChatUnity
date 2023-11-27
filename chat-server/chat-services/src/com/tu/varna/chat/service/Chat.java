package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.ChatDuo;
import com.tu.varna.chat.common.dto.UserDto;

import java.util.Set;

public interface Chat {
    void createChat(int userSender,int userReceiver);
    void sendMessage(int userSender,int userReceiver);
    void receiveMessage(int userSender,int userReceiver);
    void getChatrelation(int userSender,int userReceiver);
    ChatDuo getChatDuo();
    Set<UserDto> getChatsUsers();
    void addToChatGrup(int conversationId, int userId);
}
