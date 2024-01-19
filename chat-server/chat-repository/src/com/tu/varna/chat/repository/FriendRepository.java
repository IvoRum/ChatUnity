package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.dto.GroupDto;
import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.common.dto.UserNotFriendDto;

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
        // Number of alterations #-4
        String sql = "select  DISTINCT fr.id_friend, uu.first_name, uu.family_name, ucr.id_conversation as ucr_of_friend, cc.id_conversation as ucr_of_user " +
                "from friend_relation fr " +
                "         join public.unity_user uu on uu.id = fr.id_friend " +
                "         join public.user_conversation_relation ucr on fr.id_friend= ucr.id_user " +
                "         join public.conversation c on c.id = ucr.id_conversation " +
                "         join public.user_conversation_relation cc on cc.id_user=fr.id_user " +
                "where fr.id_user = ? and cc.id_conversation = ucr.id_conversation and c.id<>6";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            Set<UserHandleDto> foundFriends = new HashSet<>();
            while (resultSet.next()) {
                int friendId = resultSet.getInt("id_friend");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("family_name");
                int conversation = resultSet.getInt("ucr_of_friend");

                foundFriends.add(new UserHandleDto(friendId, firstName, lastName, conversation));
            }
            return Collections.unmodifiableSet(foundFriends);
        }
    }

    public void insertNewFriendRelation(int idUser, int idNewFriend) throws SQLException {

        String sql = "insert into friend_relation(id_user, id_friend) VALUES (?,?);";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idNewFriend);

            statement.executeUpdate();
        }
    }

    public Set<GroupDto> getAllGroups(int userId) throws SQLException {
        String sql =
                "SELECT c.conversation_name,c.id as conversation_id, " +
                        "       array_agg(DISTINCT u.id) AS user_ids, " +
                        "       COUNT(DISTINCT u.id) AS user_count " +
                        "FROM public.conversation c " +
                        "JOIN user_conversation_relation cu ON c.id = cu.id_conversation " +
                        "JOIN unity_user u ON cu.id_user = u.id " +
                        "GROUP BY c.conversation_name,c.id " +
                        "HAVING COUNT(DISTINCT u.id) > 2 AND ARRAY[?]::int[] <@ array_agg(DISTINCT u.id)::int[];";
        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            Set<GroupDto> foundGroups = new HashSet<>();
            while (resultSet.next()) {
                String name = resultSet.getString("conversation_name");
                int conversationId = resultSet.getInt("conversation_id");

                foundGroups.add(new GroupDto(name,conversationId));
            }
            return Collections.unmodifiableSet(foundGroups);
        }
    }

    public boolean addFriedn(final int userId, final int friendId) throws SQLException {
        String sql="INSERT INTO friend_relation(id_friend,id_user) " +
                "VALUES(?,?); " +
                "INSERT INTO friend_relation(id_friend,id_user) " +
                "VALUES(?,?); " +
                "INSERT INTO conversation(conversation_name,id) " +
                "VALUES(?,((select cc.id from conversation cc " +
                "order by cc.id desc limit 1)+1)); " +
                "INSERT INTO user_conversation_relation(id_conversation,id_user) " +
                "VALUES((select cc.id from conversation cc " +
                "order by cc.id desc limit 1),?); " +
                "INSERT INTO user_conversation_relation(id_conversation,id_user) " +
                "VALUES((select cc.id from conversation cc " +
                "order by cc.id desc limit 1),?); ";// +
                //"INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp); ";// +
                //"VALUES('Hello',(select cc.id from conversation cc " +
               // "order by cc.id desc limit 1),?,1,2,CURRENT_TIMESTAMP);";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, friendId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            statement.setInt(4, friendId);
            statement.setString(5,String.valueOf(userId)+String.valueOf(friendId));
            statement.setInt(6,userId);
            statement.setInt(7,friendId);

            return statement.execute();
        }
    }

    public List<UserNotFriendDto> getfriendsToAdd(final int userId) throws SQLException {
        String sql="select unity_user.id,unity_user.first_name,unity_user.family_name from unity_user " +
                "where unity_user.id not in(select fr.id_friend from friend_relation fr " +
                "where fr.id_user=?) and unity_user.id <>?";

        Connection connection = DriverManager.getConnection(JDBC_URL);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();

            List<UserNotFriendDto> foundFriends = new ArrayList<>();
            while (resultSet.next()) {
                int friendId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("family_name");

                foundFriends.add(new UserNotFriendDto(friendId, firstName, lastName));
            }
            return Collections.unmodifiableList(foundFriends);
        }
    }
}
