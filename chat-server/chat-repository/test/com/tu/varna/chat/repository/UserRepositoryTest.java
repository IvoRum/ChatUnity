package com.tu.varna.chat.repository;

import com.tu.varna.chat.common.dto.UserDto;
import com.tu.varna.chat.factory.jpa.JpaChatUnityRepositoryFactory;
import com.tu.varna.chat.model.UnityUser;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private JpaChatUnityRepositoryFactory jpaChatUnityRepositoryFactory;

    private UserRepository underTest;

    @BeforeEach
    void initUserRepo() {
        jpaChatUnityRepositoryFactory=new JpaChatUnityRepositoryFactory();
        underTest = jpaChatUnityRepositoryFactory.getUserRepository();
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

    @Test
    void fildAllUsers(){
        List<UnityUser> recivedUsers;
        final EntityTransaction transaction=jpaChatUnityRepositoryFactory.getManager().getTransaction();
        transaction.begin();

        recivedUsers=underTest.getAllUsersSuccessful();
        assertEquals(3,recivedUsers.size());
    }
}