package com.example.chat_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chat_mobile.ui.theme.ChatmobileTheme
import android.widget.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        // get reference to all views
        var et_email = findViewById(R.id.editTextTextEmailAddress) as EditText
        var et_password = findViewById(R.id.editTextTextPassword) as EditText
        var btn_reset = findViewById(R.id.reset) as Button
        var btn_submit = findViewById(R.id.loginButt) as Button

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_email.setText("")
            et_password.setText("")
        }

        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_email.text;
            val password = et_password.text;
            Toast.makeText(this@MainActivity, user_name, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same

            //start activiti
            val intent = Intent(this, ChatActivity::class.java)
            // start your next activity
            startActivity(intent)

        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {
    Text(
        text = "Hello My Chat app!",
        modifier = modifier
    )
}

@Composable
fun LogInForm( modifier: Modifier = Modifier){

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatmobileTheme {
        Greeting()
        LogInForm()
    }
}