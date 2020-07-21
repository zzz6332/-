package com.example.exam.repository


import com.example.exam.bean.ArticleBannerData
import com.example.exam.bean.ArticleRecomData
import com.example.exam.bean.BannerArticle
import com.example.exam.model.ReadArticleModel
import com.example.exam.net.ArticleApi
import com.example.exam.net.RetrofitManager
import retrofit2.Call
import retrofit2.Response


class ReadArticleRepository {
    private val model: ReadArticleModel = ReadArticleModel()
    fun getBannerData(
        baseurl: String,
        listner: (data: ArticleBannerData?) -> Unit
    ) {
        model.getBannerData(baseurl, listner)
    }

    fun getRecomData(
        baseurl: String,
        page: Int,
        listener: (data: ArticleRecomData?) -> Unit
    ) {
        model.getRecomData(baseurl, page, listener)
    }
}