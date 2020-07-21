package com.example.exam.Utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.exam.App
import com.example.exam.R
import com.example.exam.bean.MemoType
import com.example.exam.view.widget.RoundImageView
import kotlinx.android.synthetic.main.activity_edit_memo.*
import java.util.*

/**一些顶层函数
 *
 */
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(App.context).load(url).into(imageView)
}

fun getDate(time: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    val yaer = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return "${yaer}年${month}月${dayOfMonth}日"
}

fun getMemoType(text: String): Int {
    when (text) {
        "未分类" -> {
            return MemoType.NORMAL
        }
        "工作" -> {
            return MemoType.WORK
        }
        "作业" -> {
            return MemoType.HOMEWORK
        }
        "生活" -> {
            return MemoType.LIFE
        }
        "旅游" -> {
            return MemoType.TRAVEL
        }
        "个人" -> {
            return MemoType.PERSONAL
        }
    }
    return 0
}

fun getMemoText(type: Int): String {
    when (type) {
        MemoType.NORMAL -> {
            return "未分类"
        }
        MemoType.WORK -> {
            return "工作"

        }
        MemoType.TRAVEL -> {
            return "旅游"

        }
        MemoType.PERSONAL -> {
            return "个人"

        }
        MemoType.LIFE -> {
            return "生活"

        }
        MemoType.HOMEWORK -> {
            return "作业"
        }
    }
    return ""
}