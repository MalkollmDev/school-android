package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.malkollm.school_android.models.LessonItem

interface GroupScheduleApi {
    @GET("Lessons/GetGroupSchedule/{id}")
    suspend fun getGroupSchedule(
        @Path("id") id: Int
    ): Response<List<LessonItem>>

    @GET("Lessons/GetSchedule")
    suspend fun getSchedule(
    ): Response<List<LessonItem>>
}