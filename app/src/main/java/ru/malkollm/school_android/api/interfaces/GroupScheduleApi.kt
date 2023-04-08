package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.malkollm.school_android.models.Homework
import ru.malkollm.school_android.models.Lesson
import ru.malkollm.school_android.models.Schedule
import ru.malkollm.school_android.models.ScheduleDate

interface GroupScheduleApi {
    @GET("Lessons/GetGroupSchedule/{id}")
    suspend fun getGroupSchedule(
        @Path("id") id: Int
    ): Response<List<Lesson>>

    @GET("Lessons/GetSchedule")
    suspend fun getScheduleAll(
    ): Response<List<Lesson>>

    @GET("ScheduleDates")
    suspend fun getScheduleDates(
    ): Response<List<ScheduleDate>>

    @GET("ScheduleDates/GetScheduleByDateMobile/{date}/{id}")
    suspend fun getSchedule(
        @Path("date") date: String,
        @Path("id") id: Int
    ): Response<List<Schedule>>
}