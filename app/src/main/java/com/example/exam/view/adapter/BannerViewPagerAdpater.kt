package com.example.exam.view.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.exam.Utils.loadImage
import androidx.viewpager.widget.PagerAdapter
import com.example.exam.R
import com.example.exam.bean.BannerArticle
import com.example.exam.view.activity.ArticleActivity

class BannerViewPagerAdpater(private val activity: Activity?, private val list: List<BannerArticle>) :
    PagerAdapter() {
    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun instantiateItem(
        container: ViewGroup,
        position: Int
    ): Any {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_vp_banner, container, false)
        val iv = view.findViewById<ImageView>(R.id.item_vp_banner)
        if (list.isNotEmpty()) {
            var posi = position % list.size
            if (posi < 0) {
                posi += list.size
            }
            loadImage(iv, list[posi].imagePath)
            iv.setOnClickListener {
                goToArticleActivity(posi)
            }
            val viewParent = view.parent
            if (viewParent != null) {
                val parent = viewParent as ViewGroup
                parent.removeView(view)
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        // container.removeView(`object` as View)
    }

    private fun goToArticleActivity(position: Int) {
        val bundle = Bundle()
        bundle.putString("url", list[position].url)
        val intent = Intent(activity, ArticleActivity::class.java)
        intent.putExtra("bundle", bundle)
        activity?.startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.stay)
    }
}