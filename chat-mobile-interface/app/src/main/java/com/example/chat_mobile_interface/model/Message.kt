package com.example.chat_mobile_interface.model
data class MessageReachedPointDto(
    val firstName: String,
    val sende: Int,
    val idReceiver: Int,
    val messageOrder: Int,
    val content: String
)
data class UnreadMessage(val userSender: String, val content: String)