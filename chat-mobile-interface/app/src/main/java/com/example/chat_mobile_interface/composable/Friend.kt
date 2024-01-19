package com.example.chat_mobile_interface.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.chat_mobile_interface.model.GroupDto
import com.example.chat_mobile_interface.model.LogdInUser
import com.example.chat_mobile_interface.model.UserHandleDto
import com.example.chat_mobile_interface.ui.theme.bodyLarge
import com.example.chat_mobile_interface.view.model.FriendViewModel
import com.example.chat_mobile_interface.view.model.UserViewModel

@Composable
fun Home(viewModel: UserViewModel, navController: NavHostController, userData: State<LogdInUser>) {
    val user = viewModel.logedDataFlow.collectAsState()
    val friendViewModel =
        viewModel<FriendViewModel>(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FriendViewModel(
                    userData.value.id
                ) as T
            }
        })
    val list = friendViewModel.dataFlow.collectAsState()
    DisposableEffect(Unit) {
        friendViewModel.getFriendsUserHandle(user.value.id)
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
    Divider()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(friends.value) { item ->
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier.clickable {
                    val navstring = "chat/${item.conversation}/${item.firstName}";
                    navController.navigate(navstring)
                }) {
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.firstName, style = bodyLarge)
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.familyName, style = bodyLarge)
                }

            }
        }
    }
    Divider()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriend(
    viewModel: UserViewModel,
    navController: NavHostController,
    userData: State<LogdInUser>
) {
    val user = viewModel.logedDataFlow.collectAsState()
    val friendViewModel =
        viewModel<FriendViewModel>(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FriendViewModel(
                    userData.value.id
                ) as T
            }
        })
    val statingList = friendViewModel.nonFrDataFlow.collectAsState()
    DisposableEffect(Unit) {
        friendViewModel.getNonFriendsUserHandle(user.value.id)
        onDispose { }
    }


    var friends by remember {
        mutableStateOf(statingList)
    }
    var showDialog by remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }
    Divider()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(friends.value) { item ->
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier.clickable {}) {
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.firstName, style = bodyLarge)
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = item.familyName, style = bodyLarge)
                    IconButton(onClick = {
                        viewModel.addFriend(viewModel.logedDataFlow.value.id, item.id)
                        showDialog = true
                        friendViewModel.getNonFriendsUserHandle(user.value.id)
                    })
                    {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or presses the back button
                        showDialog = false
                    },
                    title = {
                        Text("You have added a new friend!")
                    },
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Friend") },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Handle the dialog confirmation action
                                showDialog = false
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
    Divider()
}

@Composable
fun Groups(
    viewModel: UserViewModel,
    navController: NavHostController,
    userData: State<LogdInUser>
) {
    val user = viewModel.logedDataFlow.collectAsState()
    val friendViewModel =
        viewModel<FriendViewModel>(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FriendViewModel(
                    userData.value.id
                ) as T
            }
        })
    val list = friendViewModel.groupDataFlow.collectAsState()
    DisposableEffect(Unit) {
        friendViewModel.getGroups(user.value.id)
        onDispose { }
    }
    GreetingGroup(navController, list)
}

@Composable
fun GreetingGroup(navController: NavHostController, statingList: State<List<GroupDto>>) {
    var friends by remember {
        mutableStateOf(statingList)
    }
    Divider()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(friends.value) { item ->
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier.clickable {
                    val navstring = "chat/${item.id}/${item.name}";
                    navController.navigate(navstring)
                }) {
                    Text(
                        text = item.id.toString(), style = bodyLarge
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(text = "${item.name}", style = bodyLarge)
                    //todo add user details to the chat windows
                }

            }
        }
    }
    Divider()
}