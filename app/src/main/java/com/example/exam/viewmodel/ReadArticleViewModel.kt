package com.example.exam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exam.bean.ArticleBannerData
import com.example.exam.bean.ArticleRecomData
import com.example.exam.bean.BannerArticle
import com.example.exam.bean.RecomArticle
import com.example.exam.repository.ReadArticleRepository

class ReadArticleViewModel : ViewModel() {
    private val repository: ReadArticleRepository = ReadArticleRepository()
    val bannerData: MutableLiveData<List<BannerArticle>> = MutableLiveData()
    val recomData: MutableLiveData<List<RecomArticle>> = MutableLiveData()
    val isRefresh: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isRefresh.value = false
    }

    fun getBannerData(baseurl: String) {
        repository.getBannerData(baseurl) {  //lambda表达式作为参数传递
                data: ArticleBannerData? ->
            bannerData.postValue(data?.data)
        }
    }

    fun getRecomData(baseurl: String, page: Int) {
        repository.getRecomData(
            baseurl,
            page
        ) { data: ArticleRecomData? -> recomData.postValue(data?.data?.datas) }
    }
}