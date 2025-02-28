package com.cafeteria.retrofit.menu


import com.cafeteria.dto.Menu
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitMenuService {

    // 메뉴 등록
    @POST("menu/insert")
    suspend fun menuInsert(@Body foodData: Menu) : Response<String>

    // 메뉴 수정
    @PUT("menu/update")
    suspend fun menuEdit(@Body menu: Menu) : Response<String>

    // 메뉴 전체 조회
    @GET("menu/list")
    suspend fun menuSearch() : Response<List<Menu>>

    // 메뉴 상세 조회
    @GET("menu/{menuId}")
    suspend fun menuDetail(@Path("menuId") menuId: Int) : Response<Menu>

    @GET("menu/main/list")
    suspend fun menuMainList(@Query("date") date: String) : Response<List<Menu>>

    // 메뉴 삭제
    @DELETE("menu/{menuId}")
    suspend fun menuDelete(@Path("menuId") menuId: Int) : Response<Boolean>
}