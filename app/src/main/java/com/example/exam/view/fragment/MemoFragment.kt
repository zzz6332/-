package com.example.exam.view.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.base.BaseFragment
import com.example.exam.database.entity.Memo
import com.example.exam.model.AppDataBase
import com.example.exam.view.activity.EditMemoActivity
import com.example.exam.view.adapter.MemoAdapter
import com.example.exam.viewmodel.MemoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_memo.*

class MemoFragment : BaseFragment() {

    private var list: MutableList<Memo> = ArrayList()
    private val viewModel = MemoViewModel()
    private lateinit var btnEdit: FloatingActionButton
    private lateinit var rv: RecyclerView
    private lateinit var tv: TextView
    private var tl: TabLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inMemo = true
        val view = inflater.inflate(R.layout.fragment_memo, container, false)
        rv = view.findViewById(R.id.rv_memo)
        btnEdit = view.findViewById(R.id.btn_memo)
        tv = view.findViewById(R.id.tv_memo_toolbar)
        tl = activity?.findViewById(R.id.tl_activity_main)
        btnEdit.setOnClickListener {
            goToEditMemo()
        }
        val mActivity = activity
        if (mActivity != null) {
            val adapter = MemoAdapter(mActivity,list)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(activity)
            viewModel.list.observe(mActivity, Observer {
                list.clear()
                list.addAll(it)
                tv.text = "${list.size}条笔记"
                adapter.notifyDataSetChanged()
            })
        }

        return view
    }

   override fun changeBackground(){
        val color = activity?.resources?.getColor(R.color.colorThickGray)
        if (color != null){
            tl?.setBackgroundColor(color)
        }
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
       }
    }
    private fun goToEditMemo() {
        val bundle = Bundle()
        bundle.putString("type", "add")
        val intent = Intent(activity, EditMemoActivity::class.java)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMemoData()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        inMemo = false
    }

    companion object{
        var inMemo = false
    }
}