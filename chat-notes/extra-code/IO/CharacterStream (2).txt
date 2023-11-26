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
public class CharacterStream {
    
    public static void main(String [] args)
    {
        try
        {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("example5.txt"));
            InputStreamReader in = new InputStreamReader(new FileInputStream("example5.txt"));

            
            out.write("reading using InputStreamReader");
            
            //out.flush(); 
            
            //out.close(); //calls the flush method
            
            //out.write("another string");
            
            out.flush();
            out.close();
            
            int data = in.read();
             while(data != -1)
            {
                System.out.print((char)data);
                data = in.read();
            }
           
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
}
