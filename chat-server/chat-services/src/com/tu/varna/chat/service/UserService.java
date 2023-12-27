package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.UserDto;
import com.tu.varna.chat.common.dto.UserHandleDto;

import java.util.Set;

public interface UserService {
    void addFriend(int userSender,int userReceiver);
    Set<UserHandleDto> allFriends(int userId);
}
