/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author catalin
 */
public class Main2 
{
    public static void main(String [] args)
    {
        int second = 1;
        try
        {
            while(second <= 100)
            {
                Thread.sleep(1000);
                System.out.println("Second: " + second);
                second++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
}
