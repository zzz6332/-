package com.example.exam.view.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.bean.RecomArticle
import com.example.exam.view.activity.ArticleActivity

class RecomAdapter(private val activity: Activity?, private val list: List<RecomArticle>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is RecomViewHolder) {
            holder.tvName.text = list[position].title
            holder.tvDate.text = list[position].niceDate
            if (list[position].author != "") {
                holder.tvAuthor.text = list[position].author
            } else {
                holder.tvAuthor.text = list[position].shareUser
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == FOOT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recom_articles_foot, parent, false)
            return FootViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recom_articles, parent, false)
            val holder = RecomViewHolder(view)
            holder.ll.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("url", list[holder.adapterPosition].link)
                val intent = Intent(activity, ArticleActivity::class.java)
                intent.putExtra("bundle", bundle)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.stay)
            }
            return holder
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    class RecomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_recom_name)
        val tvAuthor: TextView = view.findViewById(R.id.tv_item_recom_author)
        val tvDate: TextView = view.findViewById(R.id.tv_item_recom_date)
        val ll = view.findViewById<View>(R.id.item_recom_view)
    }

    class FootViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            FOOT
        } else {
            NORMAL
        }
    }

    companion object {
        val FOOT = 1
        val NORMAL = 2
    }
}