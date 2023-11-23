
# Input Stream 

```
import java.io.*;
public class IntStream {

    public static void main(String[] args) {
        try
        {
            FileInputStream inputStream = new FileInputStream("example1.txt");
            
            int data = inputStream.read();
            
            while(data != -1)
            {
                System.out.print((char)data);
                
                data = inputStream.read();
            }
            
            inputStream.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
}

```

# Output Stream

```
import java.io.*;
public class OutStream {
    
    public static void main(String [] args)
    {
        try
        {
            FileOutputStream fileOutput = new FileOutputStream("example2.txt", false);
            
            char H = 'H';
            
            fileOutput.write((char)H);
            
            String string = "Hello OutputStream!";
            
            fileOutput.write(string.getBytes());
            
            fileOutput.close();
            
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
}

```

# Data Stream 

```
import java.io.*;

public class DataStream {
    
    public static void main(String [] args)
    {
        //DataInputStream - read Java primitives
        //DataOutputStream - write Java primitives
        File file = new File("example3.txt");
        
        if(file.exists())
        {
            System.out.println("File already exists!" + file.getName());
        }
        else
        {
            try
            {
                if(file.createNewFile())
                {
                    System.out.println("File was created!");
                    System.out.println("file path: " + file.getAbsolutePath());
                }
                else
                {
                    System.out.println("Cannot create file");
                }
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }  
        
        try
        {
	        DataOutputStream out = new DataOutputStream(new
	             FileOutputStream(file.getName()));
            DataInputStream in = new DataInputStream(new
	             ileInputStream(file.getName()));
            
            out.writeInt(72);
            out.writeDouble(678.00);
            out.writeFloat(123.45F);
            
            int var1 = in.readInt();
            double var2 = in.readDouble();
            float var3 = in.readFloat();
            
            System.out.println("integer value: " + var1);
            System.out.println("double value:  " + var2);
            System.out.println("float value:   " + var3);
              
            out.close();
        }
        catch(Exception e)
        {
                System.out.println(e.toString());
        }
    }
}

```


# Character Steam
## Part I
```
import java.io.*;
public class CharacterStream {
    
    public static void main(String [] args)
    {
        try
        {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("example4.txt"));
            InputStreamReader in = new InputStreamReader(new FileInputStream("example4.txt"));

            
            out.write("Hello CharacterStream!");
            
            out.flush(); 
            
			out.write("another string");
            
            out.flush();
            out.close();
            
                       
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
}

```
Close() will call flush 
## Part II
```
import java.io.*;
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

```
The defout char set will be gotton from the system.

## Part III
```

import java.io.*;
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

```

# BufferedStream
Buffered sends a lot of bytes at the same tyme.


```
import java.io.*;
public class BufferStream 
{
    public static void main(String args [])
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("example6.txt"), 1024);
            BufferedWriter writer = new BufferedWriter(new FileWriter("example7.txt"));
            
            String line = null;
            
            while((line = reader.readLine()) != null)
            {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            reader.close();
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
}
```

# Print Stream

```
import java.io.*;
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

```

# System in

```
import java.io.*;
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

```


