package ru.malkollm.school_android.models

data class Schedule(
    val date: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val isReady: Boolean,
    val lastName: String,
    val lessonBreak: String,
    val lessonEnd: String,
    val lessonName: String,
    val lessonStart: String,
    val middleName: String,
    val phone: String
)