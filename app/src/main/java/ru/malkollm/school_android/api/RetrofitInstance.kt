package ru.malkollm.school_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.malkollm.school_android.api.interfaces.LessonApi
import ru.malkollm.school_android.api.interfaces.TodoApi

object RetrofitInstance {
    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    val apiLessons: LessonApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LessonApi::class.java)
    }
}