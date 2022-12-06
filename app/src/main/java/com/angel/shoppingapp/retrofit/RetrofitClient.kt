package com.angel.shoppingapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        var api: Api? = null
        fun getInstance(): Api {
            if (api == null) {
                api = Retrofit.Builder()
                    .baseUrl("https://api.escuelajs.co/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(Api::class.java)
            }
            return api!!
        }
    }
}