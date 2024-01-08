package com.example.chat_mobile_interface.view.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.FriendRepo
import com.example.chat_mobile_interface.service.TcpClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.ArrayList

class FriendViewModel() : ViewModel() {
    private val friendRepo = FriendRepo()
    private val _friends = MutableLiveData<List<UserHandleDto>>()
    val friendList: LiveData<List<UserHandleDto>> get() = _friends
    var simplefriends = listOf<UserHandleDto>(
        UserHandleDto(1, "Ivan", "Ivanov"),
        UserHandleDto(2, "Iliq", "Panov"),
        UserHandleDto(3, "Hristo", "Dimitrov"),
        UserHandleDto(4, "Mariq", "Ilieva"),
        UserHandleDto(5, "Tosho", "Kykata")
    )

    private val _dataFlow = MutableStateFlow<List<UserHandleDto>>(emptyList())
    val dataFlow: StateFlow<List<UserHandleDto>> = _dataFlow
    //val da: MutableStateFlow(emptyList())
    fun getFriendsUserHandle(
        id: Int?
    ) {
        viewModelScope.launch{//async {
            val da =friendRepo.getFriends()
            //_friends.postValue(da.await())
            _friends.value=da
            _dataFlow.value=da
            println("da+dataflow+"+_dataFlow.value)
            //simplefriends = da
        }
    }
    //////
}