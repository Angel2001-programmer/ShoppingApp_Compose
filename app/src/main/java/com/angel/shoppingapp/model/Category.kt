package com.angel.shoppingapp.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("keyLoremSpace")
    val keyLoremSpace: String,
    @SerializedName("name")
    val name: String,
)