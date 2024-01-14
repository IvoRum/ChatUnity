package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.dto.UserHandleDto;

import java.sql.*;
import java.util.*;

public class FriendRepository extends BaseRepository {


    /**
     * Retries a given user friend handles..
     *
     * @param userId id of the user that will receive all he's friends.
     * @return A set of all friends handles. If they are no friend the list will be empty. can not be NULL.
     * @throws SQLException When there is  a problem with the query.
     */
    public Set<UserHandleDto> getFriendOfuserNew(int userId) throws SQLException {

        String sql="select fr.id_friend, uu.first_name, uu.family_name, count(m.message_order) as ms_order " +
                "from friend_relation fr " +
                "         join public.unity_user uu on uu.id = fr.id_friend " +
                "join user_conversation_relation uc on uu.id = uc.id_user " +
                "join conversation c on uc.id_conversation=c.id " +
                "join public.message m on c.id=m.id_reciver " +
                "where fr.id_user = ?" +
                "group by fr.id_friend, uu.first_name, uu.family_name";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            Set<UserHandleDto> foundFriends = new HashSet<>();
            while (resultSet.next()) {
                int friendId=resultSet.getInt("id_friend");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("family_name");

                foundFriends.add(new UserHandleDto(friendId,firstName,lastName));
            }
            return Collections.unmodifiableSet(foundFriends);
        }
    }

    /**
     * Retries a given user friend handles..
     *
     * @param userId id of the user that will receive all he's friends.
     * @return A set of all friends handles. If they are no friend the list will be empty. can not be NULL.
     * @throws SQLException When there is  a problem with the query.
     */
    public Set<UserHandleDto> getFriendOfuser(int userId) throws SQLException {

        String sql = "select fr.id_friend, uu.first_name, uu.family_name " +
                "from friend_relation fr " +
                "         join public.unity_user uu on uu.id = fr.id_friend " +
                "where fr.id_user = ?";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            Set<UserHandleDto> foundFriends = new HashSet<>();
            while (resultSet.next()) {
                int friendId=resultSet.getInt("id_friend");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("family_name");

                foundFriends.add(new UserHandleDto(friendId,firstName,lastName));
            }
            return Collections.unmodifiableSet(foundFriends);
        }
    }

    public void insertNewFriendRelation(int idUser, int idNewFriend) throws SQLException {

        String sql="insert into friend_relation(id_user, id_friend) VALUES (?,?);";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2,idNewFriend);

            statement.executeUpdate();
        }
    }


}
