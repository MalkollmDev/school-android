package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.malkollm.school_android.models.Lesson

interface GroupScheduleApi {
    @GET("Lessons/GetGroupSchedule/{id}")
    suspend fun getGroupSchedule(
        @Path("id") id: Int
    ): Response<List<Lesson>>

    @GET("Lessons/GetSchedule")
    suspend fun getSchedule(
    ): Response<List<Lesson>>
}