package ru.malkollm.school_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.malkollm.school_android.api.interfaces.*

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

    val apiSchedules: ScheduleApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScheduleApi::class.java)
    }

    val apiGroups: GroupApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApi::class.java)
    }

    val apiGroupSchedule: GroupScheduleApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupScheduleApi::class.java)
    }
}