package com.tu.varna.chat.test.net.chat;

import com.tu.varna.chat.net.chat.ChatHandler;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ChatHandlerTest {

    @Test
    public void getAllConversationsForAgivenUserTest(){
        ChatHandler underTest=new ChatHandler(new Socket());

        underTest.getAllConversationsForAgivenUser("gms: 1 1@3 1@2");
    }
}