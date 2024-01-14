package com.example.chat_mobile_interface.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chat_mobile_interface.R
import com.example.chat_mobile_interface.view.model.UserViewModel

@Composable
fun Profile(viewModel: UserViewModel) {
    val user = viewModel.logedDataFlow.collectAsState()
    Column {
        Text(text = "ID: ${user.value.id}")
        Spacer(modifier = Modifier.width(13.dp))
        Row {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Profile picer"
            )
            Text(text = "${user.value.firstName} ${user.value.familyName}")
        }
    }
}