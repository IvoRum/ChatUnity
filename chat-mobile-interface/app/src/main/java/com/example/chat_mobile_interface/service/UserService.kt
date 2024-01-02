package com.example.chat_mobile_interface.service

import com.example.chat_mobile_interface.model.UserHandleDto

class UserService {

    fun getUserHandle(id:Int?): UserHandleDto {
        return UserHandleDto(1,"Ivancho","Panov")
    }
}

