package com.example.evidencia3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.evidencia3.data.RetrofitServiceFactory
import com.example.evidencia3.data.validate_user.LoginRequest
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
                val loginRequest = LoginRequest(username.text.toString(), password.text.toString())
                val resultado = service.getIfUserIsValid(loginRequest)

                if (resultado.statusCode == 200) {
                    Toast.makeText(this@LogIn, "Login correcto", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LogIn, "Login incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}