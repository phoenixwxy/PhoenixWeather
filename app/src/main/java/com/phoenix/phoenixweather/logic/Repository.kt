package com.phoenix.phoenixweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.phoenix.phoenixweather.logic.model.Place
import com.phoenix.phoenixweather.logic.network.PhoenixWeatherNetwork
import kotlinx.coroutines.Dispatchers

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
}