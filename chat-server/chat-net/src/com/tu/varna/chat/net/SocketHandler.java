package com.tu.varna.chat.net;

import java.net.Socket;

class SocketHandler extends Thread {
    private Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // use the socket here
    }
}
