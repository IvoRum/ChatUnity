package com.tu.varna.chat.application;

import com.tu.varna.chat.repository.UserRepository;

import java.sql.SQLException;

public
class Main {
    public static
    void main(String[] args) {
        System.out.println("Hello world!");
        UserRepository repo=new UserRepository();
        try {
            System.out.println(repo.userRepositoryHeathCheck());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}