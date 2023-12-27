package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.dto.UserDto;
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
    public void addFriend(int userSender, int userReceiver) {
        
    }

    @Override
    public Set<UserHandleDto> allFriends(int userId) {
        try {
            return friendRepository.getFriendOfuser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
