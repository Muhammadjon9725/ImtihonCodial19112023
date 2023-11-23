package com.example.retrofit2.models

import java.io.Serializable

data class  MyTodo(
    val holat: String,
    val id: Int,
    val matn: String,
    val oxirgi_muddat: String,
    val sarlavha: String
):Serializable