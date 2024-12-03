package com.example.evidencia3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.evidencia3.data.RetrofitServiceFactory
import com.example.evidencia3.data.get_projects.JSONBody
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainScreen : AppCompatActivity() {
    private lateinit var nombrecolaborador: TextView
    private lateinit var listView: ListView
    private lateinit var bottonNavigationMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)

        nombrecolaborador = findViewById(R.id.nombrecolaborador)
        bottonNavigationMenu = findViewById(R.id.bottomNavigationMenu)
        listView = findViewById(R.id.ListaProyectos)

        // Obtén el nombre de usuario y el servicio
        val username = intent.getStringExtra("username")
        val service = RetrofitServiceFactory.makeRetrofitServiceFactory()

        lifecycleScope.launch {
            val projectsRequest = JSONBody(username!!)
            val resultado = service.getProyects(projectsRequest)
            val proyectos = resultado.body.proyectos

            val nombresDeProyectos = proyectos.map { it.nombreDelProyecto }

            listView.adapter = ArrayAdapter(
                this@MainScreen,
                android.R.layout.simple_list_item_1,
                nombresDeProyectos
            )

            nombrecolaborador.text = "${resultado.body.colaborador.nombre} ${resultado.body.colaborador.apellidoPaterno} ${resultado.body.colaborador.apellidoMaterno}"

            // Configura el listener para el ListView
            listView.setOnItemClickListener { _, _, position, _ ->
                // Obtén el id del proyecto seleccionado
                val proyectoSeleccionado = proyectos[position]

                // Crea un Intent y pasa el id del proyecto
                val intent = Intent(this@MainScreen, DetailedProjectScreen::class.java)
                intent.putExtra("proyectoId", proyectoSeleccionado.idProyecto)

                // Inicia la nueva actividad
                startActivity(intent)
            }
        }
    }
}
