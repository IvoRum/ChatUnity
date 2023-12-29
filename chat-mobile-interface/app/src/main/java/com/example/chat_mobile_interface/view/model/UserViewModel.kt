package com.example.chat_mobile_interface.view.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.chat_mobile_interface.repository.UserRepository

class UserViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel()  {
}