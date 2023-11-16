package com.tu.varna.ropository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static String loadProperty(String key) throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        String value = null;
        try {
            value = properties.getProperty(key);
        } catch (Exception e) {
            throw new IllegalArgumentException(e+key);
        }

        return value;
    }
}
