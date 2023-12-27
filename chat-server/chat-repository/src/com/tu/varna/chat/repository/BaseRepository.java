package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.PropertiesLoader;

import java.io.FileNotFoundException;

public abstract class BaseRepository {
    protected static final String JDBC_URL;

    static {
        try {
            JDBC_URL = PropertiesLoader.loadProperty("db.url");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
