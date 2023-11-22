package com.tu.varna.chat.net.practice.thred;

public
class ServerThread extends Thread{

    public ServerThread(String thredName){
        this.setName(thredName);
    }

    @Override
    public
    void run() {
        int clientNumber = 1;
        while(clientNumber != 100)
        {
            System.out.println(this.getName() + " sent data to client: " + clientNumber);
            clientNumber++;
        }
    }
}
