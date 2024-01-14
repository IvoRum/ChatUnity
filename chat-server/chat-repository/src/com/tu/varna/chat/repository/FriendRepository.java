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
    public Set<UserHandleDto> getFriendOfuser(int userId) throws SQLException {
        /*
        String sql = "select fr.id_friend, uu.first_name, uu.family_name " +
                "from friend_relation fr " +
                "         join public.unity_user uu on uu.id = fr.id_friend " +
                "where fr.id_user = ?";
         */
        String sql="select  DISTINCT fr.id_friend, uu.first_name, uu.family_name, ucr.id_conversation as ucr_of_friend, cc.id_conversation as ucr_of_user " +
                "from friend_relation fr " +
                "         join public.unity_user uu on uu.id = fr.id_friend " +
                "         join public.user_conversation_relation ucr on fr.id_friend= ucr.id_user " +
                "         join public.conversation c on c.id = ucr.id_conversation " +
                "         join public.user_conversation_relation cc on cc.id_user=fr.id_user " +
                "where fr.id_user = ? and cc.id_conversation = ucr.id_conversation";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            Set<UserHandleDto> foundFriends = new HashSet<>();
            while (resultSet.next()) {
                int friendId=resultSet.getInt("id_friend");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("family_name");
                int conversation=resultSet.getInt("ucr_of_friend");

                foundFriends.add(new UserHandleDto(friendId,firstName,lastName,conversation));
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

    public Set<UserHandleDto> getAllUsers() throws SQLException {
        String sql="select uu.id,uu.first_name,uu.family_name from unity_user uu";
        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            Set<UserHandleDto> foundFriends = new HashSet<>();
            while (resultSet.next()) {
                int friendId=resultSet.getInt("id_friend");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("family_name");
                int conversation=1;

                foundFriends.add(new UserHandleDto(friendId,firstName,lastName,conversation));
            }
            return Collections.unmodifiableSet(foundFriends);
        }
    }
}
