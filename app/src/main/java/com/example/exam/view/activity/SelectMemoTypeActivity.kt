package com.example.exam.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.exam.R
import com.example.exam.bean.MemoType
import com.example.exam.database.entity.Memo
import kotlinx.android.synthetic.main.activity_select_memo_type.*

class SelectMemoTypeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_memo_type)
        val window = window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val color = resources?.getColor(R.color.colorPrimary)
        if (color != null){
            window?.statusBarColor = color
        }
        val bundle = intent.getBundleExtra("bundle")
        val userType = bundle.getInt("type",0)
        type = userType
        view_select_memo_type_normal.setOnClickListener {
            type = MemoType.NORMAL
            select = true
            finish()
        }
        view_select_memo_type_homework.setOnClickListener {
            type = MemoType.HOMEWORK
            select = true
            finish()
        }
        view_select_memo_type_life.setOnClickListener {
            type = MemoType.LIFE
            select = true
            finish()
        }
        view_select_memo_type_personal.setOnClickListener {
            type = MemoType.PERSONAL
            select = true
            finish()
        }
        view_select_memo_type_travel.setOnClickListener {
            type = MemoType.TRAVEL
            select = true
            finish()
        }
        view_select_memo_type_work.setOnClickListener {
            type = MemoType.WORK
            select = true
            finish()
        }
        iv_select_memo_type_back.setOnClickListener {
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay,R.anim.slide_out_top)
    }
    companion object{
        var select = false
        var type = MemoType.NORMAL
    }

}
