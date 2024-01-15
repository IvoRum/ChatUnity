package com.example.chat_mobile_interface.composable

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.chat_mobile_interface.R
import com.example.chat_mobile_interface.model.LogdInUser
import com.example.chat_mobile_interface.model.MessageReachedPointDto
import com.example.chat_mobile_interface.view.model.UserViewModel

private var chatPossituin = 0
val chatBubbleShape = RoundedCornerShape(30.dp)
@Composable
fun Chat(viewModel: UserViewModel, backStackEntry: NavBackStackEntry, userData: State<LogdInUser>) {
    val user = viewModel.logedDataFlow.collectAsState()
    val userId = backStackEntry.arguments?.getString("userData") ?: ""
    val userName = backStackEntry.arguments?.getString("userName") ?: ""

    DisposableEffect(Unit) {
        viewModel.getUserMessages(user.value.id,Integer.parseInt(userId))
        onDispose { }
    }

    ChatView(viewModel, userId.toString(), userName)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    viewModel: UserViewModel,
    conversationId: String,
    name: String
) {
    val user = viewModel.logedDataFlow.collectAsState()
    val messages = viewModel.dataFlow.collectAsState()
    var text by remember { mutableStateOf("") }

    Scaffold(topBar = {
        //TopAppBar(title = { Text(text = "User id is:$conversationId NAME: $name") })
        TopAppBar(title = { Text(text = "$name") })
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
                            viewModel.sendMessage(
                                viewModel.logedDataFlow.value.id,
                                Integer.parseInt(conversationId),
                                messages.value.get(messages.value.size - 1).messageOrder + 1,
                                text
                            )
                            viewModel.getUserMessages(user.value.id,Integer.parseInt(conversationId))
                            text = ""
                        },
                        tint = Color.Blue
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }) { Conversation(viewModel, user.value.id) }
}

@Composable
fun Conversation(viewModel: UserViewModel, userId: Int) {
    val messages = viewModel.dataFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    chatPossituin = messages.value.size
    var rememberposition = remember { chatPossituin }
    LaunchedEffect(true) {
        lazyListState.scrollToItem(rememberposition)
    }
    LaunchedEffect(chatPossituin) {
        lazyListState.scrollToItem(chatPossituin)
    }
    LazyColumn(
        reverseLayout = false, state = lazyListState,
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
        modifier = Modifier.padding(all = 8.dp),
        horizontalArrangement = Arrangement.Absolute.Center
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
                Text(text = msg.firstName, modifier = Modifier.padding(0.dp, 10.dp))
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