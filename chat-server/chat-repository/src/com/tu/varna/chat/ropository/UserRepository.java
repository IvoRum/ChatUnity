package com.tu.varna.chat.ropository;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.tu.varna.common.property.PropertiesLoader;


public class UserRepository {
    private static final String JDBC_URL;

    static {
        try {
            JDBC_URL=PropertiesLoader.loadProperty("db.url");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String userRepositoryHeathCheck() throws SQLException {

        String sql= "select Unity_user.id from Unity_user order by Unity_user.id desc limit 1;";

        Connection connection= DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("id");
        }
    }
}
