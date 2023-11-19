package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    static UserRepository underTest;

    @BeforeAll
     static void initUserRepo() {
        underTest = new UserRepository();
    }

    @Test
    void userRepositoryHeathCheck() {
        try {
            String result = underTest.userRepositoryHeathCheck();
            assertEquals("1", result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUser() {
        try {
            UserDto result = underTest.getUser(1);
            assertEquals(
                    new UserDto(1, "Ivaylo", "Rumenov", "089671253",
                            "ivo12345678", "ivo@mail.com"),
                    result
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}