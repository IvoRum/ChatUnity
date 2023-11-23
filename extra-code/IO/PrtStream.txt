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
public class PrtStream 
{
    public static void main(String [] args)
    {
        try
        {
            PrintStream out = new PrintStream("example8.txt");
            //PrintStream out = new PrintStream(new File("example8.txt"));
            
            int var1 = 10;
            
            System.out.println("The value of var1 is: " + var1);
            
            out.println("The value of var1 is: " + var1);
            //out.close();
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
        
    }
}
