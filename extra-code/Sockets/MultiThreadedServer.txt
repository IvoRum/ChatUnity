/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.net.*;
import java.io.*;

/**
 *
 * @author catalin
 */
public class MultiThreadedServer 
{
    public static void main(String [] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            boolean stop = false;
            while(!stop)
            {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client is connected.");
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
