package com.angel.shoppingapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class MoreDetailsModel : ViewModel() {
    var singleListResponse: List<productsItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getSingleItem(id: Int) {
        viewModelScope.launch {
            val api = RetrofitClient.getInstance()
            try {
                val productItem = api.getSingleItem(id)
                singleListResponse = listOf(productItem)
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}