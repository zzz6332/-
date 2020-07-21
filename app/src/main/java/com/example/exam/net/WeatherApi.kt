package com.example.exam.net

import com.example.exam.bean.CityData
import com.example.exam.bean.WeatherNowData
import com.example.exam.bean.WeatherPredictData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("v2/city/lookup")
    fun searchCity(
        @Query("location") location: String,
        @Query("mode")mode: String,
        @Query("key") key: String
    ): Call<CityData>

    @GET("v7/weather/now")
    fun getWeatherNow(
        @Query("location") location: String,
        @Query("key") key: String
    ): Call<WeatherNowData>

    @GET("v7/weather/3d")
    fun getWeatherPredict(
        @Query("location") location: String,
        @Query("key") key: String
    ): Call<WeatherPredictData>
}