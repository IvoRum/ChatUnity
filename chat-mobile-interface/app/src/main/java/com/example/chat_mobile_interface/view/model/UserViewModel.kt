package com.example.chat_mobile_interface.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.chat_mobile_interface.repository.UserRepository

class UserViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel()  {
    var userHandleDto by mutableStateOf(userRepository.getUserHandle(savedStateHandle["userId"]))

    fun getUserHandlesId(){

    }
}