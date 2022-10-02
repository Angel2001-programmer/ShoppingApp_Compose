package com.angel.shoppingapp.retrofit

import com.angel.shoppingapp.model.products
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("products")
    fun getAllProducts(): Call<products>
}
