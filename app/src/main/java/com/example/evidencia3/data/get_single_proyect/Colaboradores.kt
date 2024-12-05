package com.example.evidencia3.data.get_single_proyect

data class Colaboradores(
    val email: String,
    val jefeArea: Boolean,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val ubicacion: Ubicacion
)