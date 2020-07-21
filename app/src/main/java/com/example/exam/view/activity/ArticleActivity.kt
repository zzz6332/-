package com.example.exam.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.example.exam.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val color = resources?.getColor(R.color.colorPrimary)
        if (color != null){
            window?.statusBarColor = color
        }
        val intent = intent
        val bundle = intent.getBundleExtra("bundle")
        val url = bundle.getString("url")
        val setting = web_view.settings
        setting.javaScriptEnabled = true
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        setting.javaScriptCanOpenWindowsAutomatically = true
        setting.loadsImagesAutomatically = true
        setting.allowFileAccess = true
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                title = "Loading...."
                progress_bar.progress = newProgress
                if (progress_bar.progress>=100){
                    progress_bar.visibility = View.INVISIBLE
                }
            }
        }
        web_view.loadUrl(url)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay,R.anim.slide_out_left)
    }
}
