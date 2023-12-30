package com.example.chat_mobile_interface.view.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.UserService

class UserViewModel(
    private val
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserService
) : ViewModel() {

    companion object {
        private const val DATA_KEY = "userId"
    }
    val userHandleDto: MutableState<UserHandleDto>
        get() = mutableStateOf(userRepository.getUserHandle(savedStateHandle[DATA_KEY]))

    fun getUserHandlesId() {

    }

}