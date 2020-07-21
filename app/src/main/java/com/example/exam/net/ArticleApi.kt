package com.example.exam.net

import com.example.exam.bean.ArticleBannerData
import com.example.exam.bean.ArticleRecomData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {
    @GET("banner/json")
    fun getBannerData(): Call<ArticleBannerData>

    @GET("article/list/{page}/json")
    fun getRecomData(@Path("page") page: Int): Call<ArticleRecomData>
}