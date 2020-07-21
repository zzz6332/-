package com.example.exam.view.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.exam.R
import kotlinx.android.synthetic.main.activity_edit_memo.*
import com.example.exam.Utils.getDate
import com.example.exam.bean.MemoType
import com.example.exam.database.entity.Memo
import com.example.exam.viewmodel.EditMemoViewModel
import com.example.exam.Utils.getMemoType
import com.example.exam.Utils.getMemoText

class EditMemoActivity : AppCompatActivity() {
    private var viewModel: EditMemoViewModel = EditMemoViewModel()
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_memo)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val color = resources?.getColor(R.color.colorPrimary)
        if (color != null) {
            window?.statusBarColor = color
        }
        tv_edit_memo_date.text = getDate(System.currentTimeMillis()) //这是一个顶层函数
        val intent = intent
        val bundle = intent.getBundleExtra("bundle")
        val type = bundle.getString("type")
        et_edit_memo.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        et_edit_memo.isSingleLine = false
        et_edit_memo.setHorizontallyScrolling(false)
        if (type == "add") {
            btn_edit_memo_add.setOnClickListener {
                addMemo()
                finishActivityWithAnim()
            }
            btn_edit_memo_finish.setOnClickListener {
                addMemo()
                finishActivityWithAnim()
            }
        }
        //编辑
        else {
            val title = bundle.getString("title")
            val content = bundle.getString("content")
            val date = bundle.getString("date")
            val finish = bundle.getBoolean("finish", false)
            val id = bundle.getInt("id", 0)
            val memoType = bundle.getInt("memoType", 0)
            tv_edit_memo_type.text = getMemoText(memoType)
            et_edit_memo_title.setText(title)
            et_edit_memo.setText(content)
            setImage(memoType)
            btn_edit_memo_add.setOnClickListener {
                val title = et_edit_memo_title.text.toString()
                val content = et_edit_memo.text.toString()
                val time = System.currentTimeMillis()
                val memo = Memo(
                    id,
                    title,
                    content,
                    time,
                    false,
                    getMemoType(tv_edit_memo_type.text.toString())
                )
                viewModel.updataMemo(memo) //是通过匹配主键更新
                finishActivityWithAnim()
            }
            btn_edit_memo_finish.setOnClickListener {
                val title = et_edit_memo_title.text.toString()
                val content = et_edit_memo.text.toString()
                val time = System.currentTimeMillis()
                val memo = Memo(
                    id,
                    title,
                    content,
                    time,
                    true,
                    getMemoType(tv_edit_memo_type.text.toString())
                )
                viewModel.updataMemo(memo)
                finishActivityWithAnim()
            }
            iv_edit_memo_delete.setOnClickListener {
                val builder = AlertDialog.Builder(EditMemoActivity@ this)
                builder.setTitle("删除备忘录")
                builder.setMessage("确定删除吗？")
                builder.setNegativeButton(
                    "确定"
                ) { _, _ ->
                    val memo = Memo(id, "", "", 0, false)
                    viewModel.deleteMemo(memo)
                    finishActivityWithAnim()
                }
                builder.setPositiveButton("取消") { _, _ ->
                }
                builder.show()
            }
        }
        iv_edit_memo_back.setOnClickListener {
            finishActivityWithAnim()
        }
        tv_edit_memo_type.setOnClickListener {
            val intent = Intent(EditMemoActivity@ this, SelectMemoTypeActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("type", getType(tv_edit_memo_date.text.toString()))
            intent.putExtra("bundle", bundle)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay)
        }
    }

    override fun onResume() {
        super.onResume()
        if (SelectMemoTypeActivity.select) { //如果选择了
            val type = SelectMemoTypeActivity.type
            tv_edit_memo_type.text = getMemoText(type)
            setImage(type)
            SelectMemoTypeActivity.select = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_top)
    }

    private fun addMemo() {
        val title = et_edit_memo_title.text.toString()
        val content = et_edit_memo.text.toString()
        val time = System.currentTimeMillis()
        val memo =
            Memo(0, title, content, time, false, getMemoType(tv_edit_memo_type.text.toString()))
        viewModel.addMemo(memo)
    }

    private fun setImage(type: Int) {
        when (type) {
            MemoType.NORMAL -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book)
            }
            MemoType.WORK -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book_work)
            }
            MemoType.TRAVEL -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book_travel)
            }
            MemoType.PERSONAL -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book_personal)
            }
            MemoType.LIFE -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book_life)
            }
            MemoType.HOMEWORK -> {
                iv_edit_memo_type.setImageResource(R.drawable.ic_book_homework)
            }
        }
    }

    private fun getType(text: String): Int {
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


    private fun finishActivityWithAnim() {
        finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_out_top)
    }


}
