package com.example.exam.view.activity

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam.R
import com.example.exam.bean.City
import com.example.exam.database.entity.SearchedCity
import com.example.exam.view.adapter.CityAdapter
import com.example.exam.view.fragment.WeatherFragment
import com.example.exam.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_search_city.*

class SearchCityActivity : AppCompatActivity() {
    private val cities: ArrayList<City> = ArrayList()
    val viewmodel = WeatherViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        val adapter = CityAdapter(this, cities)
        rv_search_city.adapter = adapter
        rv_search_city.layoutManager = LinearLayoutManager(SearchCityActivity@ this)
        et_search_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    viewmodel.searchCity(
                        WeatherFragment.BASEURL_CITY,
                        s.toString(),
                        WeatherFragment.KEY
                    )
                    city = s.toString()
                } else {
                    cities.clear()
                }
            }
        })
        viewmodel.cities.observe(SearchCityActivity@ this, Observer {
            cities.clear()
            cities.addAll(it)
            adapter.notifyDataSetChanged()
        })
        viewmodel.searchedCity.observe(SearchCityActivity@ this, Observer {
            for (i in it.indices) {
                val city = it[i]
                val tv = TextView(SearchCityActivity@ this)
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(5,12,5,0)
                tv.text = city.cityName
                tv.gravity = Gravity.CENTER
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv.background = resources.getDrawable(R.drawable.background_search_city_tag_tv)
                }
                tv.layoutParams = layoutParams
                tv.setTextColor(resources.getColor(R.color.colorWhite))
                tv.textSize = 15f
                tv.setOnClickListener {
                    val sp = getSharedPreferences("city", Context.MODE_PRIVATE)
                    val editor = sp.edit()
                    editor.putString("cityName", city.cityName)
                    editor.putString("cityId", city.cityId)
                    editor.commit()
                    finish()
                    overridePendingTransition(R.anim.stay, R.anim.slide_out_left)
                }
                search_city_tag_view.addView(tv)
            }
        })
        viewmodel.getAllSearchedCity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_left)
    }


    companion object {
        const val MODE = "fuzzy" //搜索城市的mode
        var city = ""
    }
}
