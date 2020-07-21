package com.example.exam.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    private var mRetrofit: Retrofit

    companion object {
        private var instance: RetrofitManager? = null
        private var mUrl: String = ""
        fun getInstance(url: String): RetrofitManager? {
            if (instance == null || mUrl == "" || mUrl != url) {
                synchronized(this) {
                    if (instance == null || mUrl == "" || mUrl != url) {
                        instance = RetrofitManager(url)
                        mUrl = url
                    }
                }
            }
            return instance
        }
    }

    private constructor(url: String) {
        mUrl = url
        mRetrofit = Retrofit.Builder()
            .baseUrl(mUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }


    fun <T> creat(servie: Class<T>): T {
        return mRetrofit.create(servie)
    }
}