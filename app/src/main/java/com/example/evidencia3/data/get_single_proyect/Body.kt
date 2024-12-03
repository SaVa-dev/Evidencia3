package com.example.evidencia3.data.get_single_proyect

data class Body(
    val colaboradores: List<String>,
    val proyecto: Proyecto,
    val success: Boolean
)