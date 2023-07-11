package com.simranweathertest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kwabenaberko.openweathermaplib.constant.Languages
import com.kwabenaberko.openweathermaplib.constant.Units
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather
import com.simranweathertest.api.ApiConstants
import com.simranweathertest.api.Lce
import com.simranweathertest.databinding.ActivityMainBinding
import com.simranweathertest.model.WeatherViewModel
import com.simranweathertest.model.weatherResponse.WeatherCitiesResponseItem
import com.simranweathertest.utils.SharedPreference
import com.simranweathertest.utils.Utility


class MainActivity : AppCompatActivity() {
    lateinit var helper: OpenWeatherMapHelper
    lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initUis()
        initObservers()
    }

    // this is the first method called
    private fun initUis() = binding.apply() {
        helper = OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY))
        helper!!.setUnits(Units.IMPERIAL)
        helper!!.setLanguage(Languages.ENGLISH)
        tvGo.setOnClickListener(View.OnClickListener {
            if (etCityName.text.toString().isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.please_enter_city),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                callGetCitiesApi(binding.etCityName.text.toString())
            }
        })
        showWeatherInfo()
    }

    // this method check the weather of a city
    private fun checkWeather(lat: Double, lon: Double) {
        helper.getCurrentWeatherByGeoCoordinates(lat, lon, object : CurrentWeatherCallback {
            override fun onSuccess(currentWeather: CurrentWeather) {
                SharedPreference.putSharedPreferencesString(
                    this@MainActivity,
                    ApiConstants.CITY,
                    "${currentWeather.name}, ${currentWeather.sys.country}"
                )
                SharedPreference.putSharedPreferencesString(
                    this@MainActivity,
                    ApiConstants.TEMP_OF_CITY,
                    "${currentWeather.main.tempMax}"
                )
                SharedPreference.putSharedPreferencesString(
                    this@MainActivity,
                    ApiConstants.DESCRIPTION,
                    "${currentWeather.weather[0].description}"
                )
                showWeatherInfo()
            }

            override fun onFailure(throwable: Throwable) {
                Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // the function is used to get the cities list
    private fun callGetCitiesApi(city: String) {
        viewModel.getCities(city, getString(R.string.OPEN_WEATHER_MAP_API_KEY))
    }

    private fun initObservers() {
        viewModel.getCitiesData.observe(this, Observer {
            when (it) {
                Lce.Loading -> showLoading()
                is Lce.Content -> apiDataResponseView(it.content)
                is Lce.Error -> showCitiesError(it.error)
            }
        })
    }

    private fun showCitiesError(error: String) {
        Utility.hideDialog()

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        Utility.showDialog(this)
    }

    private fun apiDataResponseView(content: List<WeatherCitiesResponseItem>) {
        Utility.hideDialog()
        if (content.isNotEmpty()) {
            showCitiesList(binding.etCityName, content)
        } else {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.please_type_correct_city),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //shows the popup for cities if the entered city exist
    private fun showCitiesList(optionMenu: EditText, content: List<WeatherCitiesResponseItem>) {
        val popup = PopupMenu(this, optionMenu)
        content.forEachIndexed { index, s ->
            popup.menu.add(
                Menu.NONE,
                index + 1,
                Menu.NONE,
                s.name
            )
        }

        popup.setOnMenuItemClickListener { item: MenuItem ->
            binding.etCityName.setText(item.title)
            binding.etCityName.setSelection(binding.etCityName.length())

            for (i in content.indices) {
                if (content[i].name.equals(
                        item.title.toString(),
                        ignoreCase = true
                    )
                ) {
                    checkWeather(content.get(i).lat, content.get(i).lon)
                }
            }
            false
        }
        popup.show()
    }

    private fun showWeatherInfo() = binding.apply()
    {
        tvCity.setText(
            SharedPreference.getSharedPreferencesString(
                this@MainActivity,
                ApiConstants.CITY
            )
        )
        tvTemp.setText(
            SharedPreference.getSharedPreferencesString(
                this@MainActivity,
                ApiConstants.TEMP_OF_CITY
            )
        )
        tvDescription.setText(
            SharedPreference.getSharedPreferencesString(
                this@MainActivity,
                ApiConstants.DESCRIPTION
            )
        )
    }


}