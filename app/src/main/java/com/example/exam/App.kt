package com.example.exam

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.example.exam.view.widget.RoundImageView

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Application
    }
}