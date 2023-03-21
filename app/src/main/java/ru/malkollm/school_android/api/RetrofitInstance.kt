package ru.malkollm.school_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.malkollm.school_android.api.interfaces.*

object RetrofitInstance {
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

    val apiHomework: HomeworkApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeworkApi::class.java)
    }

    val apiUser: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.malkollm.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}