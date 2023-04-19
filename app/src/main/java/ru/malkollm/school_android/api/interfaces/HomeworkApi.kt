package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.malkollm.school_android.models.Homework

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
}