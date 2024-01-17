package com.tu.varna.chat.net.chat;

import java.net.Socket;

public class SendMessage extends BaseChatAction{


    public SendMessage(final Socket socket, final String received) {
        super(socket, received);
    }

    @Override
    public void run() {
        sendMessage();
    }

    /**
     * The order of a 'sms' is as follows:
     * sms: userId converstion order $content
     * EX
     * sms: 1 1 43 $Hello Deme
     */
    private void sendMessage() {
        String[] inputPackage = received.split("sms:");
        String[] packagePartsContent = inputPackage[1].split("\\$");
        String[] packageParts = packagePartsContent[0].split("\\s+");

        // save to db
        chatService.sendMessage(Integer.parseInt(packageParts[1]), Integer.parseInt(packageParts[2]), Integer.parseInt(packageParts[3]), packagePartsContent[1]);
        // return message
        output.println(">" + inputPackage[1]);
    }
}
