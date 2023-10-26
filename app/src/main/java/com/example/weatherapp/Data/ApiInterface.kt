package com.example.weatherapp.Data

import com.example.weatherapp.Data.FiveDayForecast.FiveDayForecast
import com.example.weatherapp.Data.Model.CurrentWeather
import com.example.weatherapp.Utils.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String = Util.apiKey
    ):Response<CurrentWeather>

    @GET("forecast?")
    suspend fun getFiveDayForecast(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") apiKey:String = Util.apiKey
    ):Response<FiveDayForecast>

}