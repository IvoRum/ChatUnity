package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.PropertiesLoader;
import jakarta.persistence.EntityManager;

import java.io.FileNotFoundException;
import java.sql.*;

public class MassageRepository {

    private static final String JDBC_URL;

    static {
        try {
            JDBC_URL= PropertiesLoader.loadProperty("db.url");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String userMessageHeathCheck() throws SQLException {

        String sql= "select message.content from message " +
                "where id_sender=1 " +
                "and id_reciver=1 " +
                "limit 1";

        Connection connection= DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("content");
        }
    }

}
