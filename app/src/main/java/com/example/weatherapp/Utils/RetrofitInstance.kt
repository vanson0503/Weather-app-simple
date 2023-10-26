package com.example.weatherapp.Utils

import com.example.weatherapp.Data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api:ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}