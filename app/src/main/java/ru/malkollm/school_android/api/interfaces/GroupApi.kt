package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.Group

interface GroupApi {
    @GET("Groups")
    suspend fun getGroups(): Response<List<Group>>
}