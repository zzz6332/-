package com.example.exam.repository

import com.example.exam.bean.CityData
import com.example.exam.bean.WeatherNowData
import com.example.exam.bean.WeatherPredictData
import com.example.exam.database.entity.SearchedCity
import com.example.exam.model.WeatherModel

class WeatherRepository {
    private val model: WeatherModel = WeatherModel()

    fun addSearchedCity(city: SearchedCity) {
        model.addSearchedCity(city)
    }

    fun getAllSearchedCity(l: (list: List<SearchedCity>) -> Unit) {
        model.getAllSearchedCity(l)
    }

    fun searchCity(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: CityData?) -> Unit
    ) {
        model.searchCity(baseurl, location, key, listener)
    }

    fun getWeatherNow(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: WeatherNowData?) -> Unit
    ) {
        model.getWeatherNow(baseurl, location, key, listener)
    }

    fun getWeatherPredict(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: WeatherPredictData?) -> Unit
    ) {
        model.getWeatherPredict(baseurl, location, key, listener)
    }
}