package com.example.weatherapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Data.FiveDayForecast.FiveDayForecast
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Util
import java.text.SimpleDateFormat

class ForecastWeatherAdapter(val fiveDayForecast: FiveDayForecast):RecyclerView.Adapter<ForecastWeatherAdapter.ForecastWeatherViewHlder>() {
    class ForecastWeatherViewHlder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.txtTime)
        val icon = itemView.findViewById<ImageView>(R.id.imageIcon)
        val desc = itemView.findViewById<TextView>(R.id.txtForecastDescription)
        val temp = itemView.findViewById<TextView>(R.id.txtForecastTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherViewHlder {
        return ForecastWeatherViewHlder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item,parent,false))
    }

    override fun getItemCount(): Int {
        return fiveDayForecast.list.size
    }

    override fun onBindViewHolder(holder: ForecastWeatherViewHlder, position: Int) {
        holder.apply {
            val sdf2 = SimpleDateFormat("dd/MM\nHH:mm")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sdf.parse(fiveDayForecast.list[position].dt_txt)
            time.setText(sdf2.format(date))
            Glide.with(icon)
                .load("${Util.baseUrlImage}${fiveDayForecast.list[position].weather[0].icon}.png")
                .into(icon)
            desc.setText(fiveDayForecast.list[position].weather[0].description.capitalize())
            temp.setText("${fiveDayForecast.list[position].main.temp} ºc")
        }
    }
}