package com.example.chat_mobile_interface

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_mobile_interface.composable.AddFriend
import com.example.chat_mobile_interface.composable.Chat
import com.example.chat_mobile_interface.composable.Groups
import com.example.chat_mobile_interface.composable.Home
import com.example.chat_mobile_interface.composable.LogIn
import com.example.chat_mobile_interface.composable.Profile
import com.example.chat_mobile_interface.service.MessageService
import com.example.chat_mobile_interface.ui.theme.ChatmobileinterfaceTheme
import com.example.chat_mobile_interface.view.model.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UserViewModel>()

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        Intent(applicationContext,MessageService::class.java).also {
            it.action=MessageService.Actions.START.toString()
            startService(it)
        }
        val brush = Brush.horizontalGradient(listOf(Color(80, 120, 230), Color.White))
        setContent {
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                var userData = viewModel.logedDataFlow.collectAsState();
                Scaffold(topBar = {
                    TopAppBar(
                        modifier = Modifier.background(brush),
                        title = {
                            if (userData.value.id != 0 && userData.value.id != 404) {
                                Row {
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            painterResource(R.drawable.friendadd2),
                                            contentDescription = "Localized description",
                                            Modifier
                                                .clickable {
                                                    navController.navigate("addfriend")
                                                }
                                                .padding(3.dp, 0.dp)
                                        )
                                    }
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            painterResource(R.drawable.chat2),
                                            contentDescription = "Localized description",
                                            Modifier
                                                .clickable {
                                                    navController.navigate("home")
                                                }
                                                .padding(3.dp, 0.dp)
                                        )
                                    }
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            painterResource(R.drawable.group2),
                                            contentDescription = "Localized description",
                                            Modifier
                                                .clickable {
                                                    navController.navigate("groups")
                                                }
                                                .padding(3.dp, 0.dp)
                                        )
                                    }
                                    //Text(text = "${userData.value.id} Name:${userData.value.familyName}")
                                }
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
                            IconButton(onClick = { navController.navigate("profile") }) {
                                if (userData.value.id != 0 && userData.value.id != 404) {
                                    Row {
                                        Icon(
                                            Icons.Rounded.AccountCircle,
                                            contentDescription = "Your Profile"
                                        )
                                    }
                                }
                            }
                        },
                    )
                })
                {
                    Box(
                        modifier = Modifier
                            .padding(0.dp, 65.dp, 0.dp, 0.dp)
                    ) {
                        Divider()
                        NavHost(navController, startDestination = "login") {
                            composable("login") {
                                LogIn(viewModel, navController)
                            }
                            composable("profile") {
                                Profile(viewModel)
                            }
                            composable("home") {
                                Home(viewModel, navController, userData)
                            }
                            composable("groups") {
                                Groups(viewModel, navController, userData)
                            }
                            composable("addfriend") {
                                AddFriend(viewModel, navController, userData)
                            }
                            composable("chat/{userData}/{userName}") { backStackEntry ->
                                Chat(viewModel, backStackEntry, userData)
                            }
                        }
                    }
                }

            }
        }
    }
}

