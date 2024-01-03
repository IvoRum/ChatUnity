package com.example.chat_mobile_interface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.service.UserService
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.ui.theme.bodyLarge
import com.example.chat_mobile_interface.view.model.FriendViewModel
import com.example.chat_mobile_interface.view.model.UserViewModel
import java.util.Collections

class FriendList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        val viewModel = viewModel<FriendViewModel>(factory = object :
                            ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return FriendViewModel(
                                    Collections.emptyList()
                                ) as T
                            }
                        })
                        viewModel.getFriendsUserHandle(2)
                        Greeting3(
                            navController,
                            viewModel.friends
                        )
                    }
                    composable("chat/{userData}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userData") ?: ""
                        val viewModel = viewModel<UserViewModel>(
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return UserViewModel(
                                        userId
                                    ) as T
                                }
                            }
                        )
                        Row {
                            Text(text = viewModel.userId)
                        }
                        //val userViewMode = UserViewModel(backStackEntry.savedStateHandle,UserService())
                        //chatView(userViewMode.userHandleDto.value.id,userViewMode.userHandleDto.value.firstName)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting3(navController: NavHostController, statingList: List<UserHandleDto>) {

    var friends by remember {
        mutableStateOf(statingList)
    }

    LazyColumn(content = {
        items(friends) { item ->
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {

                Row(modifier = Modifier.clickable {
                    val navstring = "chat/${item.id.toString()}";
                    navController.navigate(navstring)
                }) {
                    Text(
                        text = item.id.toString(), style = bodyLarge
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.familyName, style = bodyLarge)
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.familyName, style = bodyLarge)
                }

            }
            Divider()
        }
    }, modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ChatmobileinterfaceTheme {
        Greeting3(
            rememberNavController(),
            listOf<UserHandleDto>(UserHandleDto(1, "Ivan", "Ivanov"))
        )
    }
}

@Composable
fun chatView(id: Int, name: String) {
    ChatmobileinterfaceTheme {
        Text(text = "User id is:$id NAME: $name")
    }
}

fun getListOfFriends(): List<UserHandleDto> {
    val userService = UserService()
    return userService.getFriendsUserHandle(2)
}
