# Local Port Scanner
```

package socket;
import java.io.*;
import java.net.*;

public class LocalPortScanner {
    
    public static void main(String [] args)
    {
        int port = 1;
        while(port <= 65535)
        {
            try
            {
                ServerSocket server = new ServerSocket(port);
            }
            catch(IOException e)
            {
                System.out.println("Port " + port + " is open!");
            }
            port++;
        }
    }
}

```

# TPC Server

```
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
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) //throws Exception
    {   
        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("wainting for clients...");
            boolean stop = false;
            //while(!stop)
            
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello  client!");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientInput = input.readLine();
            System.out.println(clientInput);
            input.close();
            out.close();
            socket.close();
            serverSocket.close();
            
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
}
```
# Multi User Server
```
package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class MultiUserServer 
{
    public static void main(String [] args) //throws Exception
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("wainting for clients...");
            boolean stop = false;
            while(!stop)
            {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello  client!");
            BufferedReader input = new BufferedReader(new  InputStreamReader(socket.getInputStream()));
            String clientInput = input.readLine();
            System.out.println(clientInput);
            input.close();
            out.close();
            socket.close();
            } 
            serverSocket.close();
            
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }   
}
```
# Multi Threaded server

````
package socket;
import java.net.*;
import java.io.*;

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
```
#InetAddress


package socket;
import java.io.*;
import java.net.*;

/**
 *
 * @author catalin
 */
public class InetAddressExample 
{
    public static void main(String [] args)
    {
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
            System.out.println(address.getHostName());
            InetAddress address2 = InetAddress.getByName("google.com");
            System.out.println(address2.getHostAddress());
            System.out.println(address2.getHostName());
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
    }
}
