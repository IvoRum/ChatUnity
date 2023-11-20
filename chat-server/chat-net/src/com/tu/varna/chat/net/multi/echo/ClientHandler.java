package com.tu.varna.chat.net.multi.echo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{

    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            input =new Scanner(socket.getInputStream());
            output=new PrintWriter(socket.getOutputStream(),true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        String received="";
        do {
            //accept message
            received=input.nextLine();

            //return message
            output.println("ECHO: "+received);
        }while (!received.equals("QUIT"));

        try{
            if(socket!=null){
                System.out.println("Closing down connection");
                socket.close();
            }
        }catch (IOException e){
            System.out.println("Unable to disconnect!");
        }
    }
}
