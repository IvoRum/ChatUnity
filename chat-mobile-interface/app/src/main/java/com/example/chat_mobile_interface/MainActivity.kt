package com.example.chat_mobile_interface

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_mobile_interface.composable.Chat
import com.example.chat_mobile_interface.composable.Home
import com.example.chat_mobile_interface.composable.LogIn
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.view.model.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UserViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
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
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                var userData = viewModel.logedDataFlow.collectAsState();
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            if (userData.value.id != 0) {
                                Text(text = "${userData.value.id} Name:${userData.value.familyName}")
                            }
                        },
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
                        NavHost(navController, startDestination = "login") {
                            composable("login") {
                                LogIn(viewModel,navController)
                            }
                            composable("home") {
                                Home(navController, userData)
                            }
                            composable("chat/{userData}/{userName}") { backStackEntry ->
                                Chat(viewModel,backStackEntry, userData)
                            }
                        }
                    }
                }

            }
        }
    }
}

