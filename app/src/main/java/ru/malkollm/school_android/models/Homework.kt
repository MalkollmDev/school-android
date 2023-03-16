package ru.malkollm.school_android.models

data class Homework(
    val date: String,
    val id: Int,
    val lessonName: String,
    val numberGroup: Int,
    val task: String
)