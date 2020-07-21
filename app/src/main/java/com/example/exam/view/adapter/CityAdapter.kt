package com.example.exam.view.adapter

import android.app.Activity
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.bean.City
import com.example.exam.database.entity.SearchedCity
import com.example.exam.view.activity.SearchCityActivity

class CityAdapter(
    val activity: SearchCityActivity,
    private val cityList: List<City>
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    val blueSpan = ForegroundColorSpan(activity?.resources.getColor(R.color.colorBlue))
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val name = cityList[position].name
        val adm = cityList[position].adm1
        val country = cityList[position].country
        val text = "$name,$adm,$country"
        val builder = SpannableStringBuilder(text)
        val index = text.indexOf(SearchCityActivity.city)
        if (index != -1){
            builder.setSpan(blueSpan,index,index+SearchCityActivity.city.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tv.text = builder
        }
        else{
            holder.tv.text = text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_city, parent, false)
        val holder = CityViewHolder(view)

        view.setOnClickListener {
            val position = holder.adapterPosition
            val sp = activity.getSharedPreferences("city",Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("cityName",cityList[position].name)
            editor.putString("cityId",cityList[position].id)
            editor.commit()
            val city = cityList[position]
            val searchedCity = SearchedCity(0,city.id,city.name)
            activity.viewmodel.addSearchedCity(searchedCity)
            activity.finish()
            activity.overridePendingTransition(R.anim.stay,R.anim.slide_out_left)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv_item_search_city_name)
    }
}