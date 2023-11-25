/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

//https://commons.apache.org/proper/commons-validator/download_validator.cgi

import org.apache.commons.validator.routines.InetAddressValidator;

//valid IP address

//192.168.0.12
//10.10.10.10
//86.123.6.230

//invalid ip addresses

//192.168.0.321
//10.10.10.10.10
//232.0.0.ac


/**
 *
 * @author catalin
 */
public class ValidateIP {
    
    public static void main(String [] args)
    {
        InetAddressValidator validator = new InetAddressValidator();
        String ipAddress = "192.168.0.321";
        boolean isValid = validator.isValid(ipAddress);
        if (isValid) {
           System.out.println(ipAddress + " is valid"); 
        }
        else
        {
           System.out.println(ipAddress + " is not valid"); 
        }
        
    }
    
    public boolean validateIpAddress(String ipAddress)
    {
        String[] numbers = ipAddress.split("\\.");
        if (numbers.length != 4)
        {
            return false;
        }
        
        for(String str: numbers)
        {
            int i = Integer.parseInt(str);
            if((i<0) || (i>255)) 
            {
                return false;
            }
        }
        return true;
    }
    
}
