package com.example.evidencia3.data.get_projects

data class Body(
    val colaborador: Colaborador,
    val proyectos: List<Proyecto>,
    val success: Boolean
)