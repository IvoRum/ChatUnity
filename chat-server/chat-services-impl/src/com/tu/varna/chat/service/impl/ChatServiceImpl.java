package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.dto.ChatDuo;
import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import com.tu.varna.chat.common.dto.UserDto;
import com.tu.varna.chat.common.net.ChatReachedPoint;
import com.tu.varna.chat.repository.MassageRepository;
import com.tu.varna.chat.service.ChatService;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ChatServiceImpl implements ChatService {

    private final static MassageRepository massageRepository;

    static {
        massageRepository=new MassageRepository();
    }
    @Override
    public void createChat(int userSender, int userReceiver) {

    }

    @Override
    public void sendMessage(int userSender, int userReceiver, String content) {

    }

    @Override
    public List<MessageReachedPointDto> receiveMessage(ChatReachedPoint chatReachedPoint) {
        try {
            return massageRepository.getAllMessagesForAGivenUser(chatReachedPoint.conversationId(),chatReachedPoint.orderReached());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getChatrelation(int userSender, int userReceiver) {

    }

    @Override
    public ChatDuo getChatDuo() {
        return null;
    }

    @Override
    public Set<UserDto> getChatsUsers() {
        return null;
    }

    @Override
    public void addToChatGrup(int conversationId, int userId) {

    }

    @Override
    public String pickMessage() {
        try {
            return massageRepository.userMessageHeathCheck();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
