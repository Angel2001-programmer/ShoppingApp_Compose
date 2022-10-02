package com.angel.shoppingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.angel.shoppingapp.model.Product
import com.angel.shoppingapp.model.products
import com.angel.shoppingapp.retrofit.Api
import com.angel.shoppingapp.retrofit.RetrofitClient
import com.angel.shoppingapp.screens.home.homePage
import com.angel.shoppingapp.ui.theme.ShoppingAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : ComponentActivity() {
    val productsAPI = RetrofitClient.getInstance().create(Api::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Content()
    }

    fun Content() {
        setContent {
            ShoppingAppTheme {
                val result = productsAPI.getAllProducts()
                result.enqueue(object : Callback<products> {
                    override fun onResponse(call: Call<products>, response: Response<products>) {
                        Log.d("Results", "onResponse: Success ${response.body()?.products}")
                    }

                    override fun onFailure(call: Call<products>, t: Throwable) {
                        Log.d("Results", "onResponse: Failed $t")
                    }

                })
                homePage()
            }
        }
    }
}

