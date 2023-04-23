package ru.malkollm.school_android.models

import android.text.Editable

data class HomeworkDto(
    val text: String,
    val date: String,
    val lessonId: Int,
    val groupId: Int,
)