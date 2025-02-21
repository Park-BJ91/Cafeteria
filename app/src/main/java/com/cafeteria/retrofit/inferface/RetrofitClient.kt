package com.cafeteria.retrofit.inferface


import com.cafeteria.retrofit.menu.RetrofitMenuService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.66:8040/"

    val retrofit: Retrofit  by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val menuService: RetrofitMenuService by lazy {
        retrofit.create(RetrofitMenuService::class.java)
    }

}