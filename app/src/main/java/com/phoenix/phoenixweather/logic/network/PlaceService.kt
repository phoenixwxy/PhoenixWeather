package com.phoenix.phoenixweather.logic.network

import com.phoenix.phoenixweather.PhoenixWeatherApplication
import com.phoenix.phoenixweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${PhoenixWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

    // TODO can do a location based on the API official website
    // https://dashboard.caiyunapp.com/user/user/info/
    // https://open.caiyunapp.com/%E5%BD%A9%E4%BA%91%E5%A4%A9%E6%B0%94_API_%E4%B8%80%E8%A7%88%E8%A1%A8
}