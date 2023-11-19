package com.tu.varna.chat.net.multi.echo;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiEchoClient {
    private static InetAddress host;
    private static final int PORT=1300;

    public static void main(String[] args){
        try{
            host=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ClientManager.sendMessages(host,PORT);
    }
}
