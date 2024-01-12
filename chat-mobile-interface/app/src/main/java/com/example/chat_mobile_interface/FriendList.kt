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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
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
val chatBubbleShape= RoundedCornerShape(30.dp)
class FriendList : ComponentActivity() {


    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Rounded.AccountCircle, contentDescription = "Your Profile"
                                )
                            }
                        },
                    )

                }) {
                    Box(
                        modifier = Modifier
                            .padding(0.dp, 65.dp, 0.dp, 0.dp)
                    ) {
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
    }
}

// Navigation composable
@Composable
fun Chat(backStackEntry: NavBackStackEntry) {
    val userId = backStackEntry.arguments?.getString("userData") ?: ""
    val userName = backStackEntry.arguments?.getString("userName") ?: ""
    val viewModel = viewModel<UserViewModel>(factory = object : ViewModelProvider.Factory {
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

    chatView(viewModel, viewModel.userId, viewModel.userName, list)
}

@OptIn(ExperimentalMaterial3Api::class)
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
fun chatView(
    viewModel: UserViewModel,
    id: String,
    name: String,
    messages: State<List<MessageReachedPointDto>>
) {
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
                        modifier = Modifier.clickable {
                            viewModel.sendMessage(text)
                        },
                        tint = Blue
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent, unfocusedIndicatorColor = Transparent
                )
            )
        }
    }) { Conversation(2, messages) }
}

@Composable
@Preview
fun chatViewPreview() {
    val viewModel = viewModel<UserViewModel>(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(
                "1", "Ivan"
            ) as T
        }
    })
    val da: State<List<MessageReachedPointDto>> = remember {
        mutableStateOf(
            listOf(
                MessageReachedPointDto(
                    1,
                    1,
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaao Deme"
                ), MessageReachedPointDto(
                    2,
                    1,
                    "Da Ivoaaj kf ghj ksdlfgjk lhdfkghkdf ghk kdfghkdf  khk jdfhl fkjaskjdh fjka sfgjkasdhfgkjdshfghljkdfhaaaaa"
                ), MessageReachedPointDto(
                    2,
                    1,
                    "Da"
                )
            )
        )
    }
    chatView(viewModel, "1", "Ivan", da)
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
        modifier = Modifier.padding(all = 8.dp), horizontalArrangement = Arrangement.Absolute.Center
    ) {

        Column {
            Row {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = msg.sende.toString(), modifier = Modifier.padding(0.dp, 10.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .background(Color.LightGray, chatBubbleShape)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = msg.content,
                    modifier = Modifier.padding(30.dp, 10.dp)
                )
            }

        }
    }
}

@Composable
fun SendMessageCard(msg: MessageReachedPointDto) {
    // Add padding around our message
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .background(Color(179, 95, 217, 85), chatBubbleShape)
        ) {
            Text(
                text = msg.content,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(20.dp, 10.dp)
            )
        }
    }
}
