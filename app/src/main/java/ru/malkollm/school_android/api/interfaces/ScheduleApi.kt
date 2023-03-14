package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.schedule.ScheduleItem

interface ScheduleApi {
    @GET("Lessons/GetSchedule")
    suspend fun getSchedules(): Response<List<ScheduleItem>>
}