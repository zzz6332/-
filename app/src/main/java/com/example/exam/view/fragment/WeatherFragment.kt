package com.example.exam.view.fragment

import android.app.Activity
import android.content.*
import android.graphics.Typeface
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.exam.R
import com.example.exam.base.BaseFragment
import com.example.exam.view.activity.SearchCityActivity
import com.example.exam.view.service.WeatherService
import com.example.exam.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*

class WeatherFragment : BaseFragment() {
    private val viewmodel = WeatherViewModel()
    private var sp: SharedPreferences? = null
    private lateinit var tvCity: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvType: TextView
    private lateinit var tvTempNow: TextView
    private lateinit var tvTimeFirstDay: TextView
    private lateinit var ivNowType: ImageView
    private lateinit var ivWeatherTypeFirstDay: ImageView
    private lateinit var tvTempFirstDay: TextView
    private lateinit var tvTimeSecondDay: TextView
    private lateinit var ivWeatherTypeSecondDay: ImageView
    private lateinit var tvTempSecondDay: TextView
    private lateinit var tvTimeThirdDay: TextView
    private lateinit var ivWeatherTypeThirdDay: ImageView
    private lateinit var tvTempThirdDay: TextView
    private lateinit var ivSearch: ImageView
    private lateinit var tvWeather: TextView
    private var tl: TabLayout? = null
    private lateinit var serviceConnection: ServiceConnection
    private var weatherService: WeatherService? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = activity?.getSharedPreferences("city", Context.MODE_PRIVATE)
        inWeather = true
        val window = activity?.window
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                weatherService = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                weatherService = (service as WeatherService.WeatherListener).getService()
            }
        }
        window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        tl = activity?.findViewById(R.id.tl_activity_main)
        initView(view)
        val mActivity = activity
        if (mActivity != null) {
            bindWeatherService(mActivity)
            viewmodel.weatherNow.observe(mActivity, Observer {
                tvTempNow.text = "${it.temp}"
                val dayOfWeek = getDayOfWeek(0)
                val text = "$dayOfWeek  ${it.obsTime.substring(11, 16)}"
                tvTime.text = text
                tvType.text = "${it.text}  风速:${it.windSpeed}m/s"
                showWeatherType(ivNowType, it.text)
                //     tvWindSpeed.text = "风速:${it.windSpeed}"
                val city = sp?.getString("cityName", "重庆")
                if (city != null) {
                    weatherService?.updateWeather(city, "${it.temp}℃    ${it.text}")
                }
            })
            viewmodel.weatherPredict.observe(mActivity, Observer {
                tvTimeFirstDay.text = getDayOfWeek(1)
                tvTimeSecondDay.text = getDayOfWeek(2)
                tvTimeThirdDay.text = getDayOfWeek(3)
                showWeatherType(ivWeatherTypeFirstDay, it[0].textDay)
                showWeatherType(ivWeatherTypeSecondDay, it[1].textDay)
                showWeatherType(ivWeatherTypeThirdDay, it[2].textDay)
                tvTempFirstDay.text = "${it[0].tempMax}℃ / ${it[0].tempMin}℃"
                tvTempSecondDay.text = "${it[1].tempMax}℃ / ${it[1].tempMin}℃"
                tvTempThirdDay.text = "${it[2].tempMax}℃ / ${it[2].tempMin}℃"
            })
        }
        ivSearch.setOnClickListener {
            startActivity(Intent(mActivity, SearchCityActivity::class.java))
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
        }
        return view
    }

    private fun initView(view: View) {
        val typeface = Typeface.createFromAsset(activity?.assets, "typeface_tv_weather.ttf")
        tvWeather = view.findViewById(R.id.tv_weather)
        tvCity = view.findViewById(R.id.tv_weather_city)
        tvDate = view.findViewById(R.id.tv_weather_now_date)
        tvTime = view.findViewById(R.id.tv_weather_now_time)
        tvTempNow = view.findViewById(R.id.tv_weather_now_temp)
        tvTempFirstDay = view.findViewById(R.id.tv_weather_predict_first_day_temp)
        tvTempSecondDay = view.findViewById(R.id.tv_weather_predict_second_day_temp)
        tvTempThirdDay = view.findViewById(R.id.tv_weather_predict_third_day_temp)
        tvTimeFirstDay = view.findViewById(R.id.tv_weather_predict_first_day_day)
        tvTimeSecondDay = view.findViewById(R.id.tv_weather_predict_second_day_day)
        tvTimeThirdDay = view.findViewById(R.id.tv_weather_predict_third_day_day)
        ivWeatherTypeFirstDay = view.findViewById(R.id.iv_weather_predict_first_day)
        ivWeatherTypeSecondDay = view.findViewById(R.id.iv_weather_predict_second_day)
        ivWeatherTypeThirdDay = view.findViewById(R.id.iv_weather_predict_third_day)
        ivSearch = view.findViewById(R.id.iv_weather_search)
        tvType = view.findViewById(R.id.tv_weather_now_type)
        ivNowType = view.findViewById(R.id.iv_weather_now_type)
        tvTempNow.typeface = typeface
    }

    private fun getDate(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        when (month) {
            1 -> return "今天  1月${dayOfMonth}日"
            2 -> return "今天  2月${dayOfMonth}日"
            3 -> return "今天  3月${dayOfMonth}日"
            4 -> return "今天  4月${dayOfMonth}日"
            5 -> return "今天  5月${dayOfMonth}日"
            6 -> return "今天  6月${dayOfMonth}日"
            7 -> return "今天  7月${dayOfMonth}日"
            8 -> return "今天  8月${dayOfMonth}日"
            9 -> return "今天  9月${dayOfMonth}日"
            10 -> return "今天  10月${dayOfMonth}日"
            11 -> return "今天  11月${dayOfMonth}日"
            12 -> return "今天  12月${dayOfMonth}日"
        }
        return ""
    }

    private fun getDayOfWeek(delay: Int): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + delay) % 7
        var result = ""
        when (dayOfWeek) {
            1 -> result = "星期日"
            2 -> result = "星期一"
            3 -> result = "星期二"
            4 -> result = "星期三"
            5 -> result = "星期四"
            6 -> result = "星期五"
            0 -> result = "星期六"
        }
        return result
    }

    private fun showWeatherType(
        imageView: ImageView,
        text: String
    ) {
        when (text) {
            "晴" -> {
                imageView.setImageResource(R.drawable.ic_sunny)
            }
            "阴" -> {
                imageView.setImageResource(R.drawable.ic_overcast)
            }
            "小雨" -> {
                imageView.setImageResource(R.drawable.ic_small_rain)
            }
            "中雨" -> {
                imageView.setImageResource(R.drawable.ic_moderate_rain)
            }
            "大雨" -> {
                imageView.setImageResource(R.drawable.ic_big_rain)
            }
            "多云" -> {
                imageView.setImageResource(R.drawable.ic_cloudy)
            }
        }
    }

    override fun changeBackground() {
        val color = activity?.resources?.getColor(R.color.colorWeatherFragmentTabBackground)
        if (color != null){
            tl?.setBackgroundColor(color)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    override fun onResume() {
        super.onResume()
        getWeatherData()
    }

    private fun getWeatherData() {
        val cityId = sp?.getString("cityId", "101040100")
        val city = sp?.getString("cityName", "重庆")
        if (cityId != null) {
            viewmodel.getWeatherNow(BASEURL_WEATHER, cityId, KEY)
            viewmodel.getWeatherPredict(BASEURL_WEATHER, cityId, KEY)
        }
        tvCity.text = city
        tvDate.text = getDate()
    }

    private fun bindWeatherService(activity: Activity) {
        val intent = Intent(activity, WeatherService::class.java)
        activity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        inWeather = false
    }

    companion object {
        const val BASEURL_WEATHER = "https://devapi.heweather.net/"
        const val BASEURL_CITY = "https://geoapi.heweather.net/"
        const val KEY = "69eb00f8b34e4c3cb3969e9a94416c70"
        var inWeather = false
    }
}