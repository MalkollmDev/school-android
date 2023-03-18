package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.Group
import ru.malkollm.school_android.models.User

interface UserApi {
    @GET("Users")
    suspend fun getUsers(): Response<List<User>>
}