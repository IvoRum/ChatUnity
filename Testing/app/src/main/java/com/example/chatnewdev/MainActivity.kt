import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.chatnewdev.R
import com.example.chatnewdev.SecondActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText: EditText = findViewById(R.id.editTextUsername)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Проверка на потребителско име и парола (тук можеш да добавиш своята логика)

            // Пример: ако потребителско име и парола са коректни, премини към ново Activity
            if (isValidCredentials(username, password)) {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Тук можеш да добавиш своята логика за валидация на потребителско име и парола
        // Например, проверка в база данни или хардкоднати стойности за демонстрация
        return username == "user" && password == "password"
    }
}
