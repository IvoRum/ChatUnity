package com.tu.varna.chat.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRepository extends BaseRepository {


    /**
     * Retries a given user friend id`s. Will be changed when user data object is done.
     *
     * @param userId id of the user that will receive all he's friends.
     * @return A list of all friends. If they are no friend the list will be empty. can not be NULL.
     * @throws SQLException When there is  a problem with the query.
     */
    public List<Integer> getFriendOfuser(int userId) throws SQLException {

        String sql="select fr.id_friend from friend_relation fr " +
                "where fr.id_user=?;";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Integer> foundFriends=new ArrayList<>();
            while(resultSet.next()){
                foundFriends.add(resultSet.getInt("id_friend"));
            }
            return foundFriends;
        }
    }
}
