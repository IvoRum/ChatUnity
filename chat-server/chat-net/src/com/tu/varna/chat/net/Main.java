package com.tu.varna.chat.net;

import com.tu.varna.chat.net.auth.AuthNet;
import com.tu.varna.chat.net.chat.archiv.ChatNet;
import com.tu.varna.chat.net.handler.NetHandler;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private Socket socket;
    public static void main(String[] args) throws IOException {
        NetHandler netHandler=new NetHandler();
        netHandler.start();
    }
}
