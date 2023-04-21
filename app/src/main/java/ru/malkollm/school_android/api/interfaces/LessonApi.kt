package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.LessonDto

interface LessonApi {
    @GET("Lessons")
    suspend fun getLessons(): Response<List<LessonDto>>
}