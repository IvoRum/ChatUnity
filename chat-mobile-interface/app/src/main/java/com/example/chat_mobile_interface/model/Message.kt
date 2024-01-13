package com.example.chat_mobile_interface.model

data class Message(val author: String, val body: String)

data class MessageReachedPointDto(
    val sende: Int,
    val idReceiver: Int,
    val messageOrder: Int,
    val content: String
)