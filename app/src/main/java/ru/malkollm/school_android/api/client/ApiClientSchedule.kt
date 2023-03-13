package ru.malkollm.school_android.api.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class ApiClient {
    var BASE_URL: String = "http://api.malkollm.ru/"
    var retrofit: Retrofit? = null

    public fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}