package com.tu.varna.chat.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static String loadProperty(String key) throws FileNotFoundException {
        Properties properties = new Properties();
        String value = null;
        try {
            FileReader databaseProperties=new FileReader("config.properties");
            properties.load(databaseProperties);
            value = properties.getProperty(key);
            databaseProperties.close();
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
        return value;
    }
}
