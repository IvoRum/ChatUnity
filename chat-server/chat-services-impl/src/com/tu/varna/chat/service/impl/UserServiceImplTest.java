package com.tu.varna.chat.service.impl;

import com.tu.varna.chat.common.dto.UserHandleDto;
import com.tu.varna.chat.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceImplTest {

    @Test
    void addFriend() {
    }

    @Test
    void allFriends() {
        //when
        UserService underTest= new UserServiceImpl();
        //then
        Set<UserHandleDto> result= underTest.allFriends(2);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1,result.size());
    }
}