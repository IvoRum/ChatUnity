package com.tu.varna.chat.net;

import com.tu.varna.chat.net.practice.thred.ServerThread;

public class Main {
    public static void main(String[] args) {
        ServerThread thread = new ServerThread("Server1");
        ServerThread thread2 = new ServerThread("Server2");
        thread2.setPriority(Thread.MAX_PRIORITY);
        System.out.println(thread2.getName() + "priority: " + thread2.getPriority());
        thread.start();
        thread2.start();
    }
}