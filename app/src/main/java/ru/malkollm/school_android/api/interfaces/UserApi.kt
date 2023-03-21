package ru.malkollm.school_android.api.interfaces

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.malkollm.school_android.models.User

interface UserApi {
    @GET("Users")
    suspend fun getUsers(): Response<List<User>>

    @Headers("Content-Type: application/json")
    @POST("Users/checkUser")
    suspend fun checkUser(@Body user: User): Response<List<User>>
}