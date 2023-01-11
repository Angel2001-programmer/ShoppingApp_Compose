package com.angel.shoppingapp.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.angel.shoppingapp.database.AppDatabase
import com.angel.shoppingapp.database.Basket
import com.angel.shoppingapp.model.productsItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BasketModel : ViewModel() {
    var productDB = AppDatabase
    var productListDB: List<Basket> by mutableStateOf(listOf())

    fun setupDB(context: Context){
        GlobalScope.launch {
            val productDB = AppDatabase.getDatabase(context)
        }
    }

    fun ReadDatabase(context: Context){
        GlobalScope.launch {
            productListDB = productDB.getDatabase(context).userDao().getAll()
        }
    }

    fun clearAll(context: Context){
        GlobalScope.launch {
            productDB.getDatabase(context).userDao().deleteAll()
        }
    }

    fun removeProduct(context: Context, basket: Basket){
        GlobalScope.launch {
            productDB.getDatabase(context).userDao().delete(basket)
        }
    }

    fun WriteDatabase(context: Context, productListResponse: List<productsItem>) {
        for (item in productListResponse){
            val basket = Basket(
                item.id, item.images[0], item.title, item.price
            )

            GlobalScope.launch {
                productDB.getDatabase(context).userDao().add(basket)
            }
        }
    }
}