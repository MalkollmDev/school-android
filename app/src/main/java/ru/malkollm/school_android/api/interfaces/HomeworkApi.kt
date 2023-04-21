package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.*
import ru.malkollm.school_android.models.Homework
import ru.malkollm.school_android.models.HomeworkDto
import ru.malkollm.school_android.models.User

interface HomeworkApi {
    @GET("Homeworks/GetHomeworkByGroupLesson/{groupId}/{lessonId}")
    suspend fun getHomeworks(
        @Path("groupId") groupId: Int,
        @Path("lessonId") lessonId: Int
    ): Response<List<Homework>>

    @GET("Homeworks/GetHomeworkByGroup/{groupId}")
    suspend fun getHomeworksByGroup(
        @Path("groupId") groupId: Int
    ): Response<List<Homework>>

    @Headers("Content-Type: application/json")
    @POST("Homeworks/AddHomework")
    suspend fun addHomework(@Body homework: HomeworkDto): Response<HomeworkDto>
}