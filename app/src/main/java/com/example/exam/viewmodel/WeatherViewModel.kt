package com.example.exam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exam.bean.City
import com.example.exam.bean.WeatherNow
import com.example.exam.bean.WeatherPredict
import com.example.exam.database.entity.SearchedCity
import com.example.exam.repository.WeatherRepository

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    var cities = MutableLiveData<List<City>>()
    var weatherNow = MutableLiveData<WeatherNow>()
    var weatherPredict = MutableLiveData<List<WeatherPredict>>()
    var searchedCity = MutableLiveData<List<SearchedCity>>()
    fun searchCity(
        baseurl: String,
        location: String,
        key: String
    ) {
        repository.searchCity(baseurl, location, key) {
            cities.postValue(it?.location)
        }
    }

    fun getWeatherNow(
        baseurl: String,
        location: String,
        key: String
    ) {
        repository.getWeatherNow(baseurl, location, key) {
            weatherNow.postValue(it?.now)
        }
    }

    fun getWeatherPredict(
        baseurl: String,
        location: String,
        key: String
    ) {
        repository.getWeatherPredict(baseurl, location, key) {
            weatherPredict.postValue(it?.daily)
        }
    }

    fun addSearchedCity(city: SearchedCity) {
        repository.addSearchedCity(city)
    }

    fun getAllSearchedCity() {
        repository.getAllSearchedCity {
            if (it.size > 21) {
                searchedCity.postValue(it.take(20).reversed())
            } else {
                searchedCity.postValue(it.reversed())
            }
        }
    }
}