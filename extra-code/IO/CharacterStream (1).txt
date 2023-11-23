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
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("example4.txt"), "ASCII");
            InputStreamReader in = new InputStreamReader(new FileInputStream("example4.txt"));

			System.out.println(out.getEncoding());
            
            //out.write("reading using InputStreamReader");
			
			out.write("こんにちは")
            
            //out.flush(); 
            
            //out.close(); //calls the flush method
            
            //out.write("another string");
            
            out.flush();
            out.close();
            
           
           
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
}
