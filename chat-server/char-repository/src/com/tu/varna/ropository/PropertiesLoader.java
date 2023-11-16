package com.tu.varna.ropository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static String loadProperty() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("path/filename"));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        String value = null;
        for(String key : properties.stringPropertyNames()) {
            value = properties.getProperty(key);
        }
        return value;
    }
}
