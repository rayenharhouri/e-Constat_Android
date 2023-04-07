package com.example.econstat_android.Services

import com.example.econstat_android.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
    }
    val userService: UserService by lazy {
        retrofit().create(UserService::class.java)
    }
}