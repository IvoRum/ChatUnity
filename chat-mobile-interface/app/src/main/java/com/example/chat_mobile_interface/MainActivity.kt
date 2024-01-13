package com.example.chat_mobile_interface

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.view.model.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING
                ), 0
            )
        }
        setContent {
            val userId = ""
            val userName = ""
            val viewModel = viewModel<UserViewModel>(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(
                        userId, userName
                    ) as T
                }
            })
            ChatmobileinterfaceTheme {
                LogIn(viewModel)
            }
        }
    }

    @Composable
    fun LogIn(viewModel: UserViewModel) {
        var userName by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Char Unity",
                modifier = Modifier.padding(0.dp, 60.dp),
                color = Color(79, 81, 216, 100),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.Monospace,
                fontSize = 56.sp
            )
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Enter email") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = { logInAction(viewModel, userName, password) },
                modifier = Modifier.padding(10.dp)
            )
            {
                Text(text = "Log in")

            }
            Button(
                onClick = { logInAction(viewModel, "ivo@mail.com", "ivo12345678") },
                modifier = Modifier.padding(10.dp), colors = ButtonDefaults.buttonColors(Color.Transparent,
                    Color.Transparent,Color.Transparent, Color.Transparent
                )
            )
            {
                Text(text = "Log in")

            }
        }
    }

    @Composable
    fun NavigateToFriendList() {
        Button(onClick = {
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }) {
            Text(text = "Open friend list")
        }
    }

    fun logInAction(viewModel: UserViewModel, email: String, password: String) {

        viewModel.logUser(email, password)
        val user = viewModel.logedDataFlow.value

        if (user.id == 404 || user.id == 0) {
        } else {
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }
    }
}

