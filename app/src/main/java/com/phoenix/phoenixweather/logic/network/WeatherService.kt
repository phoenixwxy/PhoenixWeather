package com.phoenix.phoenixweather.logic.network

import com.phoenix.phoenixweather.PhoenixWeatherApplication
import com.phoenix.phoenixweather.logic.model.DailyResponse
import com.phoenix.phoenixweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${PhoenixWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<RealtimeResponse>

    @GET("v2.5/${PhoenixWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<DailyResponse>

}