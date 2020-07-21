package com.example.exam.view.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.exam.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val thread: ExecutorService = Executors.newCachedThreadPool()
        val type = Typeface.createFromAsset(assets, "typeface_tv_welcome.TTF")
        tv_welcome.typeface = type
        tv_welcom_bottom.typeface = type
        thread.execute {
            Thread.sleep(3000)
            val intent = Intent(MainActivity@ this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.anim_alpha_to_one,
                R.anim.anim_alpha_to_zero
            )
            finish()
        }
    }
}
