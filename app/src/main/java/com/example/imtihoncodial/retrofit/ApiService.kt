package com.example.retrofit2.retrofit

import com.example.retrofit2.models.MyRequestTodo
import com.example.retrofit2.models.MyTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("plan")
    fun getAllTodo():Call<List<MyTodo>>

    @POST("plan/")
    fun addTodo(@Body myRequestTodo: MyRequestTodo):Call<MyTodo>

    @DELETE("plan/{id}/")
    fun delete(@Path("id") id:Int):Call<Int>

    @PATCH("plan/{id}/")
    fun uptade(@Path("id") id: Int, @Body post: MyRequestTodo):Call<MyTodo>
}