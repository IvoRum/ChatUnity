package com.example.chat_mobile_interface

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatmobileinterfaceTheme {
                Button(onClick = {
                    val intent = Intent(this, FriendList::class.java)
                    startActivity(intent)
                }) {
                    Text(text = "Open friend list")
                }
            }
        }
    }
}