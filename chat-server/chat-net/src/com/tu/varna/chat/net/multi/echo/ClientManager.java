package com.tu.varna.chat.net.multi.echo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager {

    public static void sendMessages(InetAddress address, int port){
        Socket socket=null;

        try{
            socket =new Socket(address,port);
            Scanner input= new Scanner(socket.getInputStream());
            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
            Scanner userEntity=new Scanner(System.in);
            String message="", response="";

            do {
                System.out.println("Enter message ('QUIT' to exit): ");
                message= userEntity.nextLine();
                output.println(message);
                response=input.nextLine();
                System.out.println("SERVER> "+ response);
            }while (!message.equals("QUIT"));
            input.close();
            userEntity.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                System.out.println("Closing connection....");
                socket.close();
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
                throw new RuntimeException(e);
            }
        }
    }
}
