package com.example.chat_mobile_interface.service

import androidx.appcompat.app.AppCompatActivity
import com.example.chat_mobile_interface.model.UserHandleDto
import java.io.BufferedReader
import java.io.OutputStream
import java.net.Socket

class UserService {

    private lateinit var socket: Socket
    private lateinit var output: OutputStream
    private lateinit var input: BufferedReader

    fun getUserHandle(id:Int?): UserHandleDto {
        return UserHandleDto(1,"Ivancho","Panov")
    }
}

