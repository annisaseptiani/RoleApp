package com.example.roleapp.domain.model

data class User(val username : String,
    val email : String, val password : String,
    val role: String) {
}