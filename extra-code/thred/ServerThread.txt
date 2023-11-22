/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author catalin
 */
public class ServerThread extends Thread
{
    public ServerThread(String threadName)
    {
        this.setName(threadName);
    }
    
    public void run()
    {
        int clientNumber = 1;
        while(clientNumber != 100)
        {
            System.out.println(this.getName() + " sent data to client: " + clientNumber);
            clientNumber++;
        }
    }
}
