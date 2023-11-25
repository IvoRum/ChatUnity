
```
package socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author catalin
 */

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLExample 
{
    public static void main(String[] args) throws Exception 
    {
        //URL
        //URLConnection
        /*
            http://example.com
        */
        URL url = new URL("http://finance.yahoo.com/q?s=ORCL");
        URLConnection myURL = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(myURL.getInputStream()));
        String inputLine;
        String pattern = "<span id=\"yfs_l84_orcl\">(.+?)</span>";
        Pattern r = Pattern.compile(pattern);
        while ((inputLine = in.readLine()) != null) 
        {
            //System.out.println(inputLine);
            if(inputLine.contains("yfs_l84_orcl"))
            {
                Matcher m = r.matcher(inputLine);
                if (m.find( )) {
                    System.out.println(m.group(1));
                }
            }
        }
        in.close();
        
    }
}
```