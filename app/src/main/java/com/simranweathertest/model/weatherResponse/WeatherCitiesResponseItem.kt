package com.simranweathertest.model.weatherResponse

import com.google.gson.annotations.SerializedName

data class WeatherCitiesResponseItem(

    @field: SerializedName("name")
    val name: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double


)