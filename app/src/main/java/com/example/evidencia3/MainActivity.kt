package com.example.evidencia3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val connectionHelper = ConnectionHelper()
            val connect = connectionHelper.connect()

            if (connect != null) {
                val result = connect.createStatement()
                                    .executeQuery("Select * FROM AndroidStudio.prueba")

                while (result.next()) {
                    println(result.getString(1))
                    println(result.getString(2))
                }
            }
        } catch (_: Exception) {

        }
    }
}