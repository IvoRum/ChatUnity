package com.example.chat_mobile_interface.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chat_mobile_interface.R
import com.example.chat_mobile_interface.view.model.UserViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun LogIn(viewModel: UserViewModel, navController: NavHostController) {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isTextFieldEmpty by derivedStateOf {
        userName.isBlank()
    }
    val isTextFieldEmptyPassword by derivedStateOf {
        password.isBlank()
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(80, 120, 230)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(painter = painterResource(R.drawable.logo1), contentDescription = "logo")
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
        if (isTextFieldEmpty) {
            Text("Please enter text", color = Color.Red)
        }
        Button(
            onClick = {
                if (!isTextFieldEmpty && !isTextFieldEmptyPassword) {
                    loading = true
                    logInAction(viewModel, userName, password, navController) { progress ->
                        currentProgress = progress
                    }
                    loading = false
                }
            },
            modifier = Modifier.padding(10.dp)
        )
        {
            Text(text = "Log in")

        }
        Button(
            onClick = {
                loading = true
                logInAction(
                    viewModel,
                    "deme@mail.com",
                    "deme12345678",
                    navController
                ) { progress ->
                    currentProgress = progress
                }
                loading = false
            },
            modifier = Modifier.padding(10.dp), colors = ButtonDefaults.buttonColors(
                Color.Transparent,
                Color.Transparent, Color.Transparent, Color.Transparent
            )
        )
        {
            Text(text = "Log in")
        }
    }
}

private fun logInAction(
    viewModel: UserViewModel,
    email: String,
    password: String,
    navController: NavHostController,
    updateProgress: (Float) -> Unit
) {
    updateProgress(40F)
    viewModel.logUser(email, password)
    val user = viewModel.logedDataFlow.value
    updateProgress(60F)
    if (user.id == 404 || user.id == 0) {
        updateProgress(100F)
    } else {
        updateProgress(100F)
        val navstring = "home";
        navController.navigate(navstring)
    }
}