package com.phoenix.phoenixweather.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phoenix.phoenixweather.R
import com.phoenix.phoenixweather.logic.model.Weather
import com.phoenix.phoenixweather.logic.model.getSky
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    companion object {
        val TAG: String = WeatherActivity::class.java.simpleName
    }

    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather) {
        val placeName = findViewById<TextView>(R.id.place_name)
        placeName.text = viewModel.placeName

        val realtime = weather.realtime
        val daily = weather.daily
        val currentTempText = "${realtime.temperature.toInt()} ℃"

        val currentTemp = findViewById<TextView>(R.id.current_temp)
        currentTemp.text = currentTempText

        val currentSky = findViewById<TextView>(R.id.current_sky)
        currentSky.text = getSky(realtime.skycon).info

        val currentPM25Text = "空气指数 ${realtime.airQuality.chn.toInt()}"
        val currentAQI = findViewById<TextView>(R.id.current_aqi)
        currentAQI.text = currentPM25Text

        val nowLayout = findViewById<RelativeLayout>(R.id.now_layout)
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        val forecastLayout = findViewById<LinearLayout>(R.id.forecast_layout)
        forecastLayout.removeAllViews()

        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)

            val dateInfo = view.findViewById<TextView>(R.id.date_info)
            val skyIcon = view.findViewById<ImageView>(R.id.sky_icon)
            val skyInfo = view.findViewById<TextView>(R.id.sky_info)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperature_info)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info

            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()}"
            temperatureInfo.text = tempText

            forecastLayout.addView(view)
        }

        val lifeIndex = daily.lifeIndex

        val coldRiskText = findViewById<TextView>(R.id.cold_risk_text)
        coldRiskText.text = lifeIndex.coldRisk[0].desc

        val dressingText = findViewById<TextView>(R.id.dressing_text)
        dressingText.text = lifeIndex.dressing[0].desc

        val ultravioletText = findViewById<TextView>(R.id.ultraviolet_text)
        ultravioletText.text = lifeIndex.ultraviolet[0].desc

        val carWashingText = findViewById<TextView>(R.id.car_washing_text)
        carWashingText.text = lifeIndex.carWashing[0].desc

        val weatherLayout = findViewById<ScrollView>(R.id.weather_layout)
        weatherLayout.visibility = View.VISIBLE
    }
}