package com.cafeteria.retrofit.menu


import com.cafeteria.dto.Menu
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetrofitMenuService {

    @POST("menu/insert")
    suspend fun foodInsert(@Body foodData: Menu) : Response<String>

    @GET("menu/full-list")
    suspend fun foodFullList() : Response<List<Menu>>

    @GET("menu/{date}")
    suspend fun foodMenuLoad(@Path("date") date: String) : Menu


}