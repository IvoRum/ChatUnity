package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.PropertiesLoader;
import com.tu.varna.chat.common.dto.UserDto;
import com.tu.varna.chat.common.net.NewUserCredentials;
import com.tu.varna.chat.common.net.UserCredentials;
import com.tu.varna.chat.model.UnityUser;
import jakarta.persistence.EntityManager;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;


public class UserRepository {
    private static final String JDBC_URL;

    static {
        try {
            JDBC_URL = PropertiesLoader.loadProperty("db.url");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String userRepositoryHeathCheck() throws SQLException {

        String sql = "select Unity_user.id from Unity_user order by Unity_user.id asc limit 1;";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("id");
        }
    }

    public UserDto getUser(int userId) throws SQLException {

        String sql = "select * from Unity_user where Unity_user.id=?;";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String familyName = resultSet.getString("family_name");
            String password = resultSet.getString("password");
            String telephone = resultSet.getString("telephone");
            String email = resultSet.getString("email");

            return new UserDto(id, firstName, familyName, telephone, password, email);
        }
    }

    public boolean isUser(UserCredentials userCredentials) throws SQLException {
        String sql = "select uu.email,uu.password from unity_user uu " +
                "where uu.email=? " +
                "and uu.password=? ";
        Connection connection = DriverManager.getConnection(JDBC_URL);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userCredentials.userEmail());
            statement.setString(2, userCredentials.password());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            String foundUserEmail = resultSet.getString("email");
            String foundPassword = resultSet.getString("password");
            return userCredentials.userEmail().equals(foundUserEmail) &&
                    userCredentials.password().equals(foundPassword);
        }
    }

    public boolean registerUser(NewUserCredentials newUserCredentials) throws SQLException {
        String sql = "INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)" +
                "VALUES (?, ?, ?, ?, ?, ?); ";
        int newId=getLastUserId();
        Connection connection = DriverManager.getConnection(JDBC_URL);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newId);
            statement.setString(1, newUserCredentials.userCredentials().userEmail());
            statement.setString(2, newUserCredentials.userCredentials().password());
            statement.setString(3, newUserCredentials.userCredentials().password());
            statement.setString(4, newUserCredentials.userCredentials().password());
            statement.setString(5, newUserCredentials.userCredentials().password());
            statement.execute();
            return true;//returns 2 if the user was succsefuly created
        }
    }

    private int getLastUserId() throws SQLException {
        String sql="Select uu.id from Unity_user uu " +
                "order by uu.id desc " +
                "limit 1";
        Connection connection = DriverManager.getConnection(JDBC_URL);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt("id");
        }
    }
}
