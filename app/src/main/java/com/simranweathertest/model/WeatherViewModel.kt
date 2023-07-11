package com.simranweathertest.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simranweathertest.api.Lce
import com.simranweathertest.model.weatherResponse.WeatherCitiesResponseItem
import com.simranweathertest.repository.WeatherRespository
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {

    companion object {
        const val TAG = "WeatherViewModel"
    }

    private val weatherRepository =WeatherRespository()
    private val _getCitiesData = MutableLiveData<Lce<List<WeatherCitiesResponseItem>>>()
    val getCitiesData: LiveData<Lce<List<WeatherCitiesResponseItem>>> = _getCitiesData


    fun getCities(city: String,appid:String) = viewModelScope.launch {
        try {
            _getCitiesData.value = Lce.loading()

            val citiesData = weatherRepository.getCities(city, appid)
            if (citiesData != null) {
                _getCitiesData.value = Lce.content(citiesData)
            }
        } catch (e: Exception) {
            _getCitiesData.value = Lce.error(e.message ?: "Unable to Fetch Cities")
        }

    }
}