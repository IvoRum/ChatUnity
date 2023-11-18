package java.com.tu.varna.chat.repository;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.tu.varna.common.dto.UserDto;
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

    public UserDto getUser(int userId) throws SQLException {

        String sql= "select * from Unity_user where Unity_user.id=?;";

        Connection connection= DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int id=resultSet.getInt("id");
            String firstName=resultSet.getString("first_name");
            String familyName=resultSet.getString("last_name");
            String password=resultSet.getString("password");
            String telephone= resultSet.getString("telephone");
            String email=resultSet.getString("email");

            return new UserDto(id,firstName,familyName, telephone,password,email);
        }
    }
}
