package com.phoenix.phoenixweather.logic.model

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
