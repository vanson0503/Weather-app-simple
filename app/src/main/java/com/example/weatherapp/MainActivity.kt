package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Adapter.ForecastWeatherAdapter
import com.example.weatherapp.Utils.RetrofitInstance
import com.example.weatherapp.Utils.Util
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentWeather()
        binding.imgSearch.setOnClickListener{
            getCurrentWeather(binding.edtSearch.text.toString())
            binding.edtSearch.text.clear()
            binding.edtSearch.clearFocus()

        }
    }

    private fun getCurrentWeather(city:String="hanoi") {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try{
                RetrofitInstance.api.getCurrentWeather(city,"metric")
            }
            catch (e:IOException){
                Log.i("ERROR", e.toString())
                return@launch
            }
            if(response.isSuccessful&&response.body()!=null){
                withContext(Dispatchers.Main){
                    val currentWeather = response.body()!!
                    val currentDate = Date()
                    val sdfDate = SimpleDateFormat("EEEE, d MMMM, yyyy")
                    val sdfTime = SimpleDateFormat("hh : mm")
                    binding.txtCurrentTime.setText(sdfTime.format(currentDate))
                    binding.txtCurrentDate.setText(sdfDate.format(currentDate))
                    binding.txtLocation.setText("${currentWeather.name}, ${currentWeather.sys.country}")
                    binding.txtTemp.setText("${currentWeather.main.temp.toInt()}ºc")
                    binding.txtTempMaxMin.setText("${currentWeather.main.temp_min.toInt()}ºc / ${currentWeather.main.temp_max.toInt()}ºc")
                    binding.txtWeatherDescription.setText(currentWeather.weather[0].description.capitalize())
                    Glide.with(binding.imgIcon)
                        .load("${Util.baseUrlImage}${currentWeather.weather[0].icon}.png")
                        .into(binding.imgIcon)
                    binding.txtThermalSensation.setText("${currentWeather.main.feels_like.toInt()}ºc")
                    binding.txtAirHumidity.setText("${currentWeather.main.humidity} %")
                    binding.txtWindSpeed.setText("${currentWeather.wind.speed} m/s")
                }
                val fiveDayForecast = RetrofitInstance.api.getFiveDayForecast(response.body()!!.coord.lat,response.body()!!.coord.lon)
                withContext(Dispatchers.Main){
                    val item = fiveDayForecast.body()!!
                    binding.txtProbabilityofRain.setText("${item.list[0].pop*100} %")
                    binding.txtUvIndex.setText("")
                    binding.fiveDayForecast.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                    binding.fiveDayForecast.adapter = ForecastWeatherAdapter(item)
                }
            }else{
                Log.i("DATA", "getCurrentWeather: NULL")
            }
        }
    }
}