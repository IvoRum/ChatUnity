package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.dto.GroupDto;
import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.repository.FriendRepository;
import com.tu.varna.chat.service.UserService;

import java.sql.SQLException;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private static FriendRepository friendRepository;

    static {
        friendRepository=new FriendRepository();
    }
    @Override
    public void addFriend(int idUser,int idNewFriend) {
        try {
            friendRepository.addFriedn(idUser,idNewFriend);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void acceptFriendRequest(int idUser, int idNewFriend) {
        try {
            friendRepository.insertNewFriendRelation(idUser,idNewFriend);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<UserHandleDto> allFriends(int userId) {
        try {
            return friendRepository.getFriendOfuser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<GroupDto> getAllGroups(int i) {
        try {
            return friendRepository.getAllGroups(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
