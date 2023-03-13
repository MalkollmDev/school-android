package ru.malkollm.school_android.api.interfaces

import retrofit2.Response
import retrofit2.http.GET
import ru.malkollm.school_android.models.Todo

interface TodoApi {
    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>
}