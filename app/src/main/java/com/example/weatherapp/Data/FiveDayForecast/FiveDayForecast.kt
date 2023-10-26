package com.example.weatherapp.Data.FiveDayForecast

data class FiveDayForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<daily>,
    val message: Int
)