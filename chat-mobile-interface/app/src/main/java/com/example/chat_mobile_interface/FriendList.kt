package com.example.chat_mobile_interface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.ui.theme.bodyLarge

class FriendList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatmobileinterfaceTheme {
                // A surface container using the 'background' color from the theme

                    Greeting3(this)

            }
        }
    }
}

@Composable
fun Greeting3(context: Context, modifier: Modifier = Modifier) {
    var friends by remember {
        mutableStateOf(listOf<UserHandleDto>())
    }
    friends = friends + UserHandleDto(1, "Ivan", "Ivanov")
    LazyColumn(content = {
        items(friends) { item ->
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Surface(onClick = {
                    val intent = Intent(context, LogIn::class.java)
                    startActivity(intent)
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


@Composable
fun GreetingPreview3() {
    ChatmobileinterfaceTheme {
    }
}


fun goToChat(context: Context, idUser: Int) {

}