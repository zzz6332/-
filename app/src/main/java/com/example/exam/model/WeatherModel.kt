package com.example.exam.model

import com.example.exam.bean.CityData
import com.example.exam.bean.WeatherNowData
import com.example.exam.bean.WeatherPredictData
import com.example.exam.database.entity.SearchedCity
import com.example.exam.net.RetrofitManager
import com.example.exam.net.WeatherApi
import com.example.exam.view.activity.SearchCityActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class WeatherModel {

    fun addSearchedCity(city: SearchedCity) {
        execute(Runnable {
            AppDataBase.instance.searchedCityDao().addSearchedCity(city)
        })
    }

    fun getAllSearchedCity(l: (list: List<SearchedCity>) -> Unit) {
        execute(Runnable {
            val list = AppDataBase.instance.searchedCityDao().getAllSearchedCity()
            l(list)
        })
    }

    fun searchCity(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: CityData?) -> Unit
    ) {
        val api = RetrofitManager.getInstance(baseurl)?.creat(WeatherApi::class.java)
        val call = api?.searchCity(location, SearchCityActivity.MODE, key)
        call?.enqueue(object : Callback<CityData> {
            override fun onFailure(
                call: Call<CityData>?,
                t: Throwable?
            ) {
            }

            override fun onResponse(
                call: Call<CityData>?,
                response: Response<CityData>?
            ) {
                if (response?.body()?.location != null) {
                    listener(response.body())
                }
            }
        })
    }

    fun getWeatherNow(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: WeatherNowData?) -> Unit
    ) {
        val api = RetrofitManager.getInstance(baseurl)?.creat(WeatherApi::class.java)
        val call = api?.getWeatherNow(location, key)
        call?.enqueue(object : Callback<WeatherNowData> {
            override fun onFailure(
                call: Call<WeatherNowData>?,
                t: Throwable?
            ) {

            }

            override fun onResponse(
                call: Call<WeatherNowData>?,
                response: Response<WeatherNowData>?
            ) {
                listener(response?.body())
            }

        })
    }

    fun getWeatherPredict(
        baseurl: String,
        location: String,
        key: String,
        listener: (data: WeatherPredictData?) -> Unit
    ) {
        val api = RetrofitManager.getInstance(baseurl)?.creat(WeatherApi::class.java)
        val call = api?.getWeatherPredict(location, key)
        call?.enqueue(object : Callback<WeatherPredictData> {
            override fun onFailure(
                call: Call<WeatherPredictData>?,
                t: Throwable?
            ) {
            }

            override fun onResponse(
                call: Call<WeatherPredictData>?,
                response: Response<WeatherPredictData>?
            ) {
                listener(response?.body())
            }

        })
    }

    private fun execute(runnable: Runnable){
        val thread = Executors.newCachedThreadPool()
        thread.execute { runnable.run() }
    }
}