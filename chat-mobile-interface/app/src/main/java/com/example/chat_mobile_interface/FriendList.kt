package com.example.chat_mobile_interface

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.ui.theme.bodyLarge
import com.example.chat_mobile_interface.view.model.FriendViewModel
import com.example.chat_mobile_interface.view.model.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

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
                        Home(navController)
                    }
                    composable("chat/{userData}/{userName}") { backStackEntry ->
                        Chat(backStackEntry)
                    }
                }
            }
        }
    }
}

// Navigation composable
@Composable
fun Chat(backStackEntry: NavBackStackEntry) {
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
    val list = viewModel.dataFlow.collectAsState()
    DisposableEffect(Unit) {
        viewModel.getUserMessages()
        onDispose { }
    }

    chatView(viewModel.userId, viewModel.userName, list)
}

@Composable
fun Home(navController: NavHostController) {
    val viewModel = viewModel<FriendViewModel>()
    val list = viewModel.dataFlow.collectAsState()
    DisposableEffect(Unit) {
        viewModel.getFriendsUserHandle(2)
        onDispose { }
    }
    Greeting3(navController, list)
}

//Sub composable
@Composable
fun Greeting3(navController: NavHostController, statingList: State<List<UserHandleDto>>) {
    var friends by remember {
        mutableStateOf(statingList)
    }
    LazyColumn(content = {
        items(friends.value) { item ->
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatView(id: String, name: String, messages: State<List<MessageReachedPointDto>>) {
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
    }) { Conversation(2, messages) }
}

@Composable
@Preview
fun chatViewPreview() {
    val da: State<List<MessageReachedPointDto>> =
        remember {
            mutableStateOf(
                listOf(
                    MessageReachedPointDto(1, 2, "Alo Deme"),
                    MessageReachedPointDto(2, 1, "Da Ivoaaaaaaaaaaaaa")
                )
            )
        }
    chatView("1", "Ivan", da)
}

@Composable
fun Conversation(userId: Int, messages: State<List<MessageReachedPointDto>>) {
    LazyColumn(
        reverseLayout = false,
        modifier = Modifier
            .padding(10.dp, 85.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(messages.value) { message ->
            if (userId == message.sende) {
                SendMessageCard(message)
            } else {
                MessageCard(message)
            }

        }
    }
}

@Composable
fun MessageCard(msg: MessageReachedPointDto) {
    // Add padding around our message
    Row(
        modifier = Modifier
            .padding(all = 8.dp), horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = msg.sende.toString())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.content)
        }
    }
}

@Composable
fun SendMessageCard(msg: MessageReachedPointDto) {
    // Add padding around our message
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.Bottom) {
                //Spacer(modifier = Modifier.width(8.dp))

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = msg.content)
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}
