package com.tu.varna.chat.service;

import com.tu.varna.chat.common.dto.GroupDto;
import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.common.dto.UserNotFriendDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    void addFriend(int idUser,int idNewFriend);
    void acceptFriendRequest(int idUser, int idNewFriend);
    Set<UserHandleDto> allFriends(int userId);
    Set<GroupDto> getAllGroups(int i);

    List<UserNotFriendDto> getNotFriend(int userId);
}
