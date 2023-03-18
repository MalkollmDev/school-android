package ru.malkollm.school_android.models

data class User(
    val email: String,
    val firstName: String,
    val groupId: Int,
    val id: Int,
    val lastName: String,
    val login: String,
    val middleName: String,
    val password: String,
    val phone: String,
    val roleId: Int
)