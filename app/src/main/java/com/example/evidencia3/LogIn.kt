package com.example.evidencia3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.evidencia3.data.RetrofitServiceFactory
import com.example.evidencia3.data.validate_user.JSONBody as JSONBodyLogin
import com.example.evidencia3.data.get_projects.JSONBody as JSONBodyProyects
import kotlinx.coroutines.launch

class LogIn : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        val service = RetrofitServiceFactory.makeRetrofitServiceFactory()
        username = findViewById(R.id.user)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)

        login.setOnClickListener {
            lifecycleScope.launch {
                val loginRequest = JSONBodyLogin(username.text.toString(), password.text.toString())
                val resultado = service.getIfUserIsValid(loginRequest)

                if (resultado.statusCode == 200) {
                    val intent = Intent(this@LogIn, MainScreen::class.java)
                    intent.putExtra("isAdmin", resultado.body.isAdmin)
                    intent.putExtra("username", username.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LogIn, "Error: ${resultado.body.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}