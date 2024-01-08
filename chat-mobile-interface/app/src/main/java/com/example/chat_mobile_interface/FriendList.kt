package com.example.chat_mobile_interface

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FriendList : ComponentActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        val viewModel = viewModel<FriendViewModel>()
                        //In bras boathouse of mock
                        /*
                        var list= emptyList<UserHandleDto>() //by viewModel.friendList.observeAsState(emptyList())
                        viewModel.friendList.observe(this@FriendList){
                            list=it
                        }
                        viewModel.getFriendsUserHandle(2)

                        LaunchedEffect(viewModel){
                            viewModel.getFriendsUserHandle(2)
                        }
                         */
                        Greeting3(
                            navController, viewModel.simplefriends
                        )
                    }
                    composable("chat/{userData}/{userName}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userData") ?: ""
                        val userName = backStackEntry.arguments?.getString("userName") ?: ""
                        val viewModel =
                            viewModel<UserViewModel>(factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return UserViewModel(
                                        userId, userName
                                    ) as T
                                }
                            })
                        val ms = listOf<Message>(
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve"),
                            Message("Ivan", "Hello from Ivan"),
                            Message("Ivan", "Ko pravish ve")
                        )

                        chatView(viewModel.userId, viewModel.userName, ms)
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
                    val navstring = "chat/${item.id}/${item.familyName}";
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
            rememberNavController(), listOf<UserHandleDto>(UserHandleDto(1, "Ivan", "Ivanov"))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun chatViewPreview() {
    val ms = listOf<Message>(
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve"),
        Message("Ivan", "Hello from Ivan"),
        Message("Ivan", "Ko pravish ve")
    )


    ChatmobileinterfaceTheme {
        chatView(id = "1", name = "Ivan", ms)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatView(id: String, name: String, messages: List<Message>) {
    var text by remember { mutableStateOf("") }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "User id is:$id NAME: $name") })
    }, bottomBar = {
        Row(modifier = Modifier.padding(10.dp)) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(60.dp),
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Send message",
                        tint = Blue
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent
                )
            )
        }
    }) { Conversation(messages) }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(
        reverseLayout = true,
        modifier = Modifier.padding(10.dp, 65.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    val SampleData =
        listOf<Message>(
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve"),
            Message("Ivan", "Hello from Ivan"),
            Message("Ivan", "Ko pravish ve")
        )
    ChatmobileinterfaceTheme {
        Box(Modifier.background(Color.Blue)) {
            Conversation(SampleData)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = msg.author)
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = msg.body)
            }
    }
}

data class Message(val author: String, val body: String)

fun getListOfFriends() = GlobalScope.async {
    val userService = UserService()
    userService.getFriendsUserHandle(2)
    //return emptyList()
}
