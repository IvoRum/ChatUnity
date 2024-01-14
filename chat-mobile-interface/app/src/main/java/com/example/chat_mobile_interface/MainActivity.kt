package com.example.chat_mobile_interface

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_mobile_interface.composable.Chat
import com.example.chat_mobile_interface.composable.Home
import com.example.chat_mobile_interface.composable.LogIn
import com.example.chat_mobile_interface.composable.Profile
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
        val brush = Brush.horizontalGradient(listOf(Color(80,120,230), Color.White))
        setContent {
            ChatmobileinterfaceTheme {
                val navController = rememberNavController()
                var userData = viewModel.logedDataFlow.collectAsState();
                Scaffold(topBar = {
                    TopAppBar(modifier=Modifier.background(brush),
                        title = {
                            if (userData.value.id != 0 && userData.value.id != 404) {
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
                            IconButton(onClick = { navController.navigate("profile") }) {
                                if (userData.value.id != 0 && userData.value.id != 404) {
                                    Icon(
                                        Icons.Rounded.AccountCircle,
                                        contentDescription = "Your Profile"
                                    )
                                }
                            }
                        },
                    )
                }) {
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
                                Home(navController, userData)
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

