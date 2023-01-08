package com.angel.shoppingapp.retrofit

import com.angel.shoppingapp.model.Category
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.model.singleProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("products")
    suspend fun getProducts(): List<productsItem>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("categories/{id}/products")
    suspend fun getChosenItems(@Path("id") id: Int): List<productsItem>

    @GET("products/{id}")
    suspend fun getSingleItem(@Path("id") id: Int): productsItem
}