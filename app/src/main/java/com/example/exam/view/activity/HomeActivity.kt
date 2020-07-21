package com.example.exam.view.activity

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.exam.R
import com.example.exam.base.BaseFragment
import com.example.exam.view.fragment.MemoFragment
import com.example.exam.view.fragment.ReadArticleFragment
import com.example.exam.view.fragment.WeatherFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_memo.*

class HomeActivity : AppCompatActivity() {
    private val fragmentList: MutableList<BaseFragment> = ArrayList()
    private val FRAGMENT_COUNT = 3
    private val tabIcon = intArrayOf(
        R.drawable.selector_read_article,
        R.drawable.selector_memo, R.drawable.selector_weather
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        fragmentList.add(ReadArticleFragment())
        fragmentList.add(MemoFragment())
        fragmentList.add(WeatherFragment())
        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        vp_activity_main.adapter = fragmentAdapter
        tl_activity_main.setupWithViewPager(vp_activity_main)
        vp_activity_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("----", "$position  $positionOffset  $positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                fragmentList[position].changeBackground()
            }

        })
        tl_activity_main.getTabAt(0)?.customView = getTabView(0)
        tl_activity_main.getTabAt(1)?.customView = getTabView(1)
        tl_activity_main.getTabAt(2)?.customView = getTabView(2)

    }

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null)
        val iv = view.findViewById<ImageView>(R.id.iv_icon)
        iv.setImageResource(tabIcon[position])
        return view
    }

    inner class FragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return FRAGMENT_COUNT
        }
    }
}
