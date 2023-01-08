package com.angel.shoppingapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.shoppingapp.model.Category
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class HomeModel() : ViewModel() {
    var productListResponse: List<productsItem> by mutableStateOf(listOf())
    var categoryListResponse: List<Category> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")

    fun getData() {
        viewModelScope.launch {
            val api = RetrofitClient.getInstance()
            try {
                val productList = api.getProducts()
                productListResponse = productList
                Log.d("API Response", "getData: $productListResponse")

            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
                Log.d("API Response", "getData: $errorMessage")
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val api = RetrofitClient.getInstance()
            try {
                val categoryList = api.getCategories()
                categoryListResponse = categoryList
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getChosenItems(id: Int) {
        viewModelScope.launch {
            val api = RetrofitClient.getInstance()
            try {
                val chosenList = api.getChosenItems(id)
                productListResponse = chosenList
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getSingleItem(id: Int) {
        viewModelScope.launch {
            val api = RetrofitClient.getInstance()
            try {
                val productItem = api.getSingleItem(id)
                productListResponse = listOf(productItem)
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}