package com.simranweathertest.api

import com.simranweathertest.model.weatherResponse.WeatherCitiesResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("direct")
    suspend fun getCities(@Query("q") city: String, @Query("appid") appid: String):
            Response<List<WeatherCitiesResponseItem>>
}