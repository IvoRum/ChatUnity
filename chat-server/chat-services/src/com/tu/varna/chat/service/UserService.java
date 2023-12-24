package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.UserDto;

import java.util.Set;

public interface UserService {
    void addFriend(int userSender,int userReceiver);
    Set<UserDto> allFriends(int userId);
}
