package com.simranweathertest.repository

import com.simranweathertest.api.ApiBuilder
import com.simranweathertest.api.WeatherService
import com.simranweathertest.api.bodyOrThrow
import com.simranweathertest.model.weatherResponse.WeatherCitiesResponseItem

class WeatherRespository {

    private val weatherService: WeatherService = ApiBuilder.create(WeatherService::class.java)

    suspend fun getCities(city: String, appid: String): List<WeatherCitiesResponseItem> {
        val otpData = weatherService.getCities(city, appid)
        return otpData.bodyOrThrow()
    }
}