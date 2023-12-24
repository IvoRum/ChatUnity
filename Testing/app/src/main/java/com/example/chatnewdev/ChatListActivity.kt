package com.example.chatnewdev

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import java.net.Socket

class ChatListActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        initSocketConnection()

        // Пример: Изпращане на заявка за чатове
        socket.emit("getChats", "user_id") // Замени "user_id" с реалния идентификатор на потребителя

        // Пример: Изпращане на заявка за приятели
        socket.emit("getFriends", "user_id") // Замени "user_id" с реалния идентификатор на потребителя
    }

    private fun initSocketConnection() {
        try {
            socket = IO.socket("https://your-server-url") // Замени с реалния URL на твоя сървър
            socket.connect()

            // Обработка на събития от сървъра
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected to server")
            }

            socket.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketIO", "Disconnected from server")
            }

            // Обработка на получени чатове
            socket.on("chats") { args ->
                runOnUiThread {
                    val chatList = args[0] as JSONArray
                    
                    Log.d("SocketIO", "Received chats: $chatList")
                }
            }

            // Обработка на получени приятели
            socket.on("friends") { args ->
                runOnUiThread {
                    val friendsList = args[0] as JSONArray
                    // Тук можеш да обработиш списъка с приятели и го покажеш в RecyclerView
                    Log.d("SocketIO", "Received friends: $friendsList")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}
