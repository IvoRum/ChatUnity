package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.PropertiesLoader;
import jakarta.persistence.EntityManager;

import java.io.FileNotFoundException;
import java.sql.*;

public class MassageRepository {

    private static final String JDBC_URL;

    static {
        try {
            JDBC_URL = PropertiesLoader.loadProperty("db.url");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String userMessageHeathCheck() throws SQLException {

        String sql = "select message.content from message " +
                "where id_sender=1 " +
                "and id_reciver=1 " +
                "limit 1";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("content");
        }
    }

    public String getMessagesForAGivenUser(int idRevicer, int messageOrder) throws SQLException {

        String sql = "select ms.id_sender, ms.message_order, ms.message_status, ms.content from message ms " +
                "where id_reciver=? " +
                "and message_order > ?";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRevicer);
            statement.setInt(2, messageOrder);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("content");
        }
    }

    public void sendMessage(int idRevicer, int messageOrder) throws SQLException {

        String sql = "insert into message(id_sender, tid_reciver, message_order, time_stamp, tcontent, tmessage_status) " +
                "value(?,?,?,now(),?,?)";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        }
    }

}
