package com.example.evidencia3.data.get_single_proyect

data class Proyecto(
    val descripcion: String,
    val fechaDeFinalizacion: String,
    val fechaDeInicio: String,
    val id: Int,
    val nombre: String,
    val presupuesto: Int,
    val status: String
)