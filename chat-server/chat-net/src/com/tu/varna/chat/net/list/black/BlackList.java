package com.tu.varna.chat.net.list.black;

import com.tu.varna.chat.common.net.UserCredentials;

public class BlackList {
    void add(){
        //TODO get the existinglist and add IP
    }

    void getUsersRemainingTime(){
        //TODO get the existing list and user to check for their time
    }

    void remove(UserCredentials userCredentials){
        //TODO check if user is in the list then remove if exist

    }

    void timeOut(UserCredentials userCredentials,int time){
        assert userCredentials==null: "UserCredentials must not be null";
        assert time<0:"Time out must be positive number";
    }
}
