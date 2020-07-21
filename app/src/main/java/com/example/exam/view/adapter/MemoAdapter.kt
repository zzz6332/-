package com.example.exam.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.database.entity.Memo
import com.example.exam.Utils.getDate
import com.example.exam.bean.MemoType
import com.example.exam.view.activity.EditMemoActivity

class MemoAdapter(val activity: Activity, val list: List<Memo>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NORMAL){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo,parent,false)
            val holer = MemoViewHolder(view)
            holer.view.setOnClickListener {
               goToEditMemo(holer)
            }
            return holer
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo_foot,parent,false)
            return MemoFootViewHolder(view)
        }
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MemoViewHolder){
            val title = list[position].title
            val content = list[position].content
            if (title == "标题" && content == ""){
                holder.tvTitle.text = "标题"
            }
            else if (title == "标题"){
                if (content.length > 20){
                    holder.tvTitle.text = "${content.substring(0,20)}..."
                }
                else {
                    holder.tvTitle.text = content
                }
            }
            else{
                holder.tvTitle.text = title
            }
            holder.tvDate.text = getDate(list[position].time)
            if (list[position].finish){
                holder.iv.visibility = View.VISIBLE
            }
            else {
                holder.iv.visibility = View.GONE
            }
              setBackground(holder.view,list[position].type)
        }
    }
    override fun getItemCount(): Int {
        return list.size + 1
    }

    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_memo_title)
        val tvDate: TextView = itemView.findViewById(R.id.tv_memo_date)
        val view: View = itemView.findViewById(R.id.card_view_memo)
        val iv: ImageView = itemView.findViewById(R.id.iv_memo)
    }
    class MemoFootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object{
        val FOOT = 1
        val NORMAL = 2
    }
    override fun getItemViewType(position: Int): Int {
        if (position == list.size){
           return FOOT
        }
        else{
            return  NORMAL
        }
    }

    private fun goToEditMemo(holder: RecyclerView.ViewHolder){
        val bundle = Bundle()
        bundle.putInt("id",list[holder.adapterPosition].id)
        bundle.putString("content",list[holder.adapterPosition].content)
        bundle.putBoolean("finish",list[holder.adapterPosition].finish)
        bundle.putString("title",list[holder.adapterPosition].title)
        bundle.putString("date",getDate(list[holder.adapterPosition].time))
        bundle.putInt("memoType",list[holder.adapterPosition].type)
        bundle.putString("type","edit")
        val intent = Intent(activity,EditMemoActivity::class.java)
        intent.putExtra("bundle",bundle)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay)
    }
    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setBackground(
        view: View,
        type: Int
    ){
        when(type){
            MemoType.WORK -> {
                view.background = activity.resources.getDrawable(R.drawable.backgounr_work_memo)
            }
            MemoType.PERSONAL -> {
                view.background = activity.resources.getDrawable(R.drawable.background_personal_memo)            }
            MemoType.TRAVEL -> {
                view.background = activity.resources.getDrawable(R.drawable.background_travel_memo)            }
            MemoType.LIFE -> {
                view.background = activity.resources.getDrawable(R.drawable.background_life_memo)            }
            MemoType.HOMEWORK -> {
                view.background = activity.resources.getDrawable(R.drawable.background_homework_memo)            }
        }
    }
}