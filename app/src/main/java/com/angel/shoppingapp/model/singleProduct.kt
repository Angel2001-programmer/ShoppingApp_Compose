package com.angel.shoppingapp.model


import com.google.gson.annotations.SerializedName

data class singleProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
)