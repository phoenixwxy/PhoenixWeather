package com.phoenix.phoenixweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.phoenix.phoenixweather.logic.model.Place
import com.phoenix.phoenixweather.logic.model.Weather
import com.phoenix.phoenixweather.logic.network.PhoenixWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = PhoenixWeatherNetwork.searchPlaces(query)

            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }

        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtime = async {
                    PhoenixWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    PhoenixWeatherNetwork.getDailyWeather(lng, lat)
                }

                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(realtimeResponse.result.realtime,
                                            dailyResponse.result.daily)

                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }

        emit(result)
    }
}