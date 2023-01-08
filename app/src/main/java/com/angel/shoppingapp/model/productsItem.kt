package com.angel.shoppingapp.model


import com.google.gson.annotations.SerializedName

data class productsItem(
    @SerializedName("category")
    val category: Category,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("images")
    val images: List<String> = emptyList(),
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("title")
    val title: String = "",
)