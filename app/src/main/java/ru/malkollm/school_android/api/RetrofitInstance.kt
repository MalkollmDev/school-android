package ru.malkollm.school_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.malkollm.school_android.api.interfaces.ScheduleApi
import ru.malkollm.school_android.api.interfaces.TodoApi

object RetrofitInstance {
    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    val apiSchedule: ScheduleApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScheduleApi::class.java)
    }
}