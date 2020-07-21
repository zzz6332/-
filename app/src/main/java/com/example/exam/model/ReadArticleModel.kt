package com.example.exam.model

import com.example.exam.bean.ArticleBannerData
import com.example.exam.bean.ArticleRecomData
import com.example.exam.net.ArticleApi
import com.example.exam.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadArticleModel {
    fun getBannerData(
        baseurl: String,
        listener: (data: ArticleBannerData?) -> Unit
    ) {
        val api = RetrofitManager.getInstance(baseurl)?.creat(ArticleApi::class.java)
        val call = api?.getBannerData()
        call?.enqueue(object : Callback<ArticleBannerData> {
            override fun onFailure(
                call: Call<ArticleBannerData>?,
                t: Throwable?
            ) {
            }

            override fun onResponse(
                call: Call<ArticleBannerData>?,
                response: Response<ArticleBannerData>?
            ) {
                listener(response?.body())
            }

        })
    }

    fun getRecomData(
        baseurl: String,
        page: Int,
        listener: (data: ArticleRecomData?) -> Unit
    ) {
        val api = RetrofitManager.getInstance(baseurl)?.creat(ArticleApi::class.java)
        val call = api?.getRecomData(page)
        call?.enqueue(object : Callback<ArticleRecomData> {
            override fun onFailure(
                call: Call<ArticleRecomData>?,
                t: Throwable?
            ) {
            }
            override fun onResponse(
                call: Call<ArticleRecomData>?,
                response: Response<ArticleRecomData>?
            ) {
                listener(response?.body())
            }
        })
    }
}