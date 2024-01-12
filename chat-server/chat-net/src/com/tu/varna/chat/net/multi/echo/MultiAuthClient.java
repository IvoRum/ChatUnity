package com.tu.varna.chat.net.multi.echo;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiAuthClient {
    private static InetAddress host;
    private static final int PORT=1301;

    public static void main(String[] args){
        try{
            host=InetAddress.getLocalHost();
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ClientManager.sendMessages(host,PORT);
    }
}
