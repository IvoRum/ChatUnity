package com.example.chat_mobile_interface.model
data class UserHandleDto(
    val id: Int,
    val firstName: String,
    val familyName: String,
    val conversation: Int
)
data class LogdInUser(val id: Int, val firstName: String, val familyName: String, val email: String)

data class GroupDto(val name: String, val id: Int)