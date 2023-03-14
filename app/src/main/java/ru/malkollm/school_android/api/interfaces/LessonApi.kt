package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.Lesson
import ru.malkollm.school_android.models.LessonItem
import ru.malkollm.school_android.models.Todo

interface LessonApi {
    @GET("lessons")
    suspend fun getLessons(): Response<List<LessonItem>>
}