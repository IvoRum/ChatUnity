package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.PropertiesLoader;
import com.tu.varna.chat.common.dto.MessageReachedPointDto;
import jakarta.persistence.EntityManager;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MassageRepository extends BaseRepository {

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

    public List<MessageReachedPointDto> getAllMessagesForAGivenUser(int idRevicer, int messageOrder) throws
                                                                                                     SQLException {
        /*
        String sql = "select ms.id_sender, ms.message_order, ms.message_status, ms.content from message ms " +
                "where id_reciver=? " +
                "and message_order > ? and message_order < ?";
         */
        String sql = "select uu.first_name, ms.id_sender, ms.message_order, ms.message_status, ms.content " +
                "from message ms " +
                "join public.unity_user uu on uu.id = ms.id_sender " +
                "where id_reciver=? " +
                "and message_order > ? and message_order < ?";
        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRevicer);
            statement.setInt(2, messageOrder);
            statement.setInt(3, messageOrder + 100);
            ResultSet resultSet = statement.executeQuery();
            List<MessageReachedPointDto> foundMessages = new ArrayList<>();
            while (resultSet.next()) {
                foundMessages.add(new MessageReachedPointDto(resultSet.getString("first_name"),
                        resultSet.getInt("id_sender"), idRevicer,
                        resultSet.getInt("message_order"), resultSet.getString("content")));
            }
            //setMessagesAsRead(idRevicer,messageOrder);
            return foundMessages;
        }
    }

    public List<MessageReachedPointDto> getAllMessagesForAGivenUserSetToRead(int idRevicer, int messageOrder) throws
                                                                                                              SQLException {
        String sql = "select uu.first_name, ms.id_sender, ms.message_order, ms.message_status, ms.content " +
                "from message ms " +
                "join public.unity_user uu on uu.id = ms.id_sender " +
                "where id_reciver=? " +
                "and message_order > ? and message_order < ?";
        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRevicer);
            statement.setInt(2, messageOrder);
            statement.setInt(3, messageOrder + 100);
            ResultSet resultSet = statement.executeQuery();
            List<MessageReachedPointDto> foundMessages = new ArrayList<>();
            while (resultSet.next()) {
                foundMessages.add(new MessageReachedPointDto(resultSet.getString("first_name"),
                        resultSet.getInt("id_sender"), idRevicer,
                        resultSet.getInt("message_order"), resultSet.getString("content")));
            }
            setMessagesAsRead(idRevicer, messageOrder);
            return foundMessages;
        }
    }

    public void sendMessage(final int idRevicer, final int idSender, final int messageOrder, final String content) throws
                                                                                                                   SQLException {
        String sql = "INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp) " +
                " VALUES(?,?,?,?,2,CURRENT_TIMESTAMP)";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, content);
            statement.setInt(2, idRevicer);
            statement.setInt(3, idSender);
            statement.setInt(4, messageOrder);
            statement.execute();
            System.out.println("Message:" + content + " Order:" + messageOrder);
        }

    }

    private void setMessagesAsRead(final int idRevicer, final int messageOrder) throws SQLException {
        String sql =
                "update public.message set message_status=3 where id_reciver=? and message_order > ? and message_order < ?";
        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRevicer);
            statement.setInt(2, messageOrder);
            statement.setInt(3, messageOrder + 100);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
