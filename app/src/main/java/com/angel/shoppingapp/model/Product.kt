package com.angel.shoppingapp.model

data class Product(
//    val brand: String,
//    val category: String,
    val description: String,
//    val discountPercentage: Double,
//    val id: Int,
//    val images: List<String>,
    val price: Double,
//    val rating: Double,
//    val stock: Int,
    val thumbnail: String,
    val title: String,
)

fun getProduct(): List<Product> {
    return listOf(

        Product(
            thumbnail = "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
            title = "iPhone",
            price = 50.00,
            description = ""
        ),

        Product(
            thumbnail = "https://dummyjson.com/image/i/products/2/thumbnail.jpg",
            title = "iPhone",
            price = 800.00,
            description = ""
        ),

        Product(
            thumbnail = "https://dummyjson.com/image/i/products/3/thumbnail.jpg",
            title = "Phone",
            price = 1000.00,
            description = ""
        )
    )
}