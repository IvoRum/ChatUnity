/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author catalin
 */
public class SystemIn 
{
    public static void main(String [] args)
    {
        System.out.print("Please enter the port number: ");
        
        InputStreamReader in = new InputStreamReader(System.in);
        
        /*
        int data = in.read();
        while(data != -1)
        {
            System.out.print((char)data);
            data = in.read();
        }
        */
        
        BufferedReader reader  = new BufferedReader(in);
        
        boolean isValid = false;
        int port = 0;
        
        while(!isValid)
        {
            try
            {
                String portString = reader.readLine();
                port = Integer.parseInt(portString);
                System.out.println("Port is accepted!");
                isValid = true;
            }
            catch(Exception e)
            {
                System.out.println("Please insert a number!");
                System.out.printf("Please enter the port number: ");
            }
        }
        
        System.out.print("Please enter Server IP address: ");
        
        String ipAddress = "";
        
        try
        {
            ipAddress = reader.readLine();
        }
        catch(Exception e)
        {
            System.out.println("Cannot read the ip address! " + e.toString());
        }
        
        System.out.println("");
        System.out.println("________________________________");
        System.out.println("");
        
        System.out.println("Trying to connect to IP: " + ipAddress + " on port: " + port + "...");
        
        
    }
}
