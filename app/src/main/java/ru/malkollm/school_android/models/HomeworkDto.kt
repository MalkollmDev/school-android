package ru.malkollm.school_android.models

data class HomeworkDto(
    val date: String,
    val id: Int,
    val lessonId: Int,
    val groupId: Int,
    val text: String
)