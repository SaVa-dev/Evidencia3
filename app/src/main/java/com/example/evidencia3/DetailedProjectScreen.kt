package com.example.evidencia3

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.evidencia3.data.RetrofitServiceFactory
import com.example.evidencia3.data.get_single_proyect.JSONBody as JSONBodySingleProyect
import com.example.evidencia3.data.search_if_user_exists_by_email.JSONBody as JSONBodySearchUser
import com.example.evidencia3.R
import com.example.evidencia3.data.get_single_proyect.GetSingleProyectResult
import kotlinx.coroutines.launch

class DetailedProjectScreen : AppCompatActivity() {
    lateinit var projectName: EditText
    lateinit var description: EditText
    lateinit var projectStatus: Spinner
    lateinit var startDate: EditText
    lateinit var endDate: EditText
    lateinit var budget: EditText
    lateinit var location: EditText
    lateinit var personsListView: ListView
    lateinit var addUserButton: Button
    lateinit var saveButton: Button
    lateinit var backButton: Button
    lateinit var resultado: GetSingleProyectResult
    lateinit var adapter: ArrayAdapter<String>

    // Lista mutable para guardar los nombres de los colaboradores
    val colaboradoresNombres = mutableListOf<String>()
    // Lista mutable para guardar los nuevos usuarios agregados
    val nuevosUsuarios = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_project_screen)

        val service = RetrofitServiceFactory.makeRetrofitServiceFactory()
        val proyectoSeleccionado = intent.getStringExtra("nombreProyecto")

        Log.d("DetailedProjectScreen", "Nombre del proyecto seleccionado: $proyectoSeleccionado")

        projectName = findViewById(R.id.projectName)
        description = findViewById(R.id.description)
        projectStatus = findViewById(R.id.projectStatus)
        startDate = findViewById(R.id.startDate)
        endDate = findViewById(R.id.endDate)
        budget = findViewById(R.id.budget)
        location = findViewById(R.id.location)
        personsListView = findViewById(R.id.personsListView)
        addUserButton = findViewById(R.id.addUserButton)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        lifecycleScope.launch {
            val loginRequest = JSONBodySingleProyect("Proyecto Alpha")
            resultado = service.getSingleProyect(loginRequest)

            if (resultado.statusCode == 200) {
                projectName.setText(resultado.body.proyecto.nombreDelProyecto)
                description.setText(resultado.body.proyecto.descripcion)
                startDate.setText(resultado.body.proyecto.fechaDeInicio)
                endDate.setText(resultado.body.proyecto.fechaDeFinalizacion)
                budget.setText(resultado.body.proyecto.presupuesto.toString())

                val nombres = resultado.body.colaboradores.map { it.nombre + " " + it.apellidoPaterno + " " + it.apellidoMaterno }

                // Agregar los colaboradores a la lista mutable
                colaboradoresNombres.addAll(nombres)

                // Crear el adapter y asignarlo al ListView
                adapter = ArrayAdapter(this@DetailedProjectScreen, android.R.layout.simple_list_item_1, colaboradoresNombres)
                personsListView.adapter = adapter
                adapter.notifyDataSetChanged()

                projectStatus.setSelection(
                    resources.getStringArray(R.array.project_status_options).indexOf(resultado.body.proyecto.status)
                )

            } else {
                Toast.makeText(this@DetailedProjectScreen, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            OnBackPressedDispatcher()
        }

        addUserButton.setOnClickListener {
            showSearchUserByEmailDialog()
        }

        saveButton.setOnClickListener {
            // Aquí puedes hacer algo con los nuevos usuarios, como enviarlos a la base de datos
            publishNewUsersToDatabase()
            val intent = Intent(this, MainScreen::class.java)
            intent.putExtra("nombreProyecto", proyectoSeleccionado)
            startActivity(intent)
        }
    }

    private fun showSearchUserByEmailDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_search_user_by_email)
        dialog.setCancelable(true)

        val cancelButton = dialog.findViewById<Button>(R.id.cancelSearchButton)
        val searchButton = dialog.findViewById<Button>(R.id.searchUserButton)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        searchButton.setOnClickListener {
            val service = RetrofitServiceFactory.makeRetrofitServiceFactory()
            val userEmail = dialog.findViewById<EditText>(R.id.usermail).text.toString()

            lifecycleScope.launch {
                val addUserRequest = JSONBodySearchUser(userEmail)
                val resultadoColaborador = service.searchIfUserExistsByEmail(addUserRequest)

                if (resultadoColaborador.statusCode == 200) {
                    Toast.makeText(this@DetailedProjectScreen, "Usuario encontrado y agregado correctamente", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                    val nombreColaborador = resultadoColaborador.body.nombre + " " +
                            resultadoColaborador.body.apellidoPaterno + " " +
                            resultadoColaborador.body.apellidoMaterno

                    colaboradoresNombres.add(nombreColaborador)
                    nuevosUsuarios.add(nombreColaborador)
                    adapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this@DetailedProjectScreen, "Error: Ingresa un correo válido", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }


    private fun publishNewUsersToDatabase() {
        Log.d("DetailedProjectScreen", "Publicando nuevos usuarios: $nuevosUsuarios")
    }
}
