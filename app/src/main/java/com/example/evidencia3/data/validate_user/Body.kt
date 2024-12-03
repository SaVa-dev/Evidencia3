package com.example.evidencia3.data.validate_user

data class Body(
    val auth: Boolean,
    val isAdmin: Boolean,
    val message: String
)