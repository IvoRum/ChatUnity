package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.UserHandleDto;

import java.util.Set;

public interface UserService {
    void addFriend(int userSender,int userReceiver);
    void acceptFriendRequest(int idUser, int idNewFriend);
    Set<UserHandleDto> allFriends(int userId);
}
