package com.example.chat_mobile_interface.repository

import com.example.chat_mobile_interface.model.UserHandleDto

class UserRepository {
    fun getUserHandle(id:Int?): UserHandleDto {
        return UserHandleDto(1,"Ivancho","Panov")
    }
}

