package com.example.exam.model

import com.example.exam.database.entity.Memo
import java.util.concurrent.Executors

class MemoModel {

    fun getMemoData(l:(list:List<Memo>) -> Unit) {
        execute(Runnable {
            val list =  AppDataBase.instance.memoDao().getMemo()
            l(list)
        })
    }


    fun addMemoData(memo: Memo) {
        execute(Runnable {
            AppDataBase.instance.memoDao().addMemo(memo)
        })
    }

    fun updateMemo(memo: Memo){
        execute(Runnable {
            AppDataBase.instance.memoDao().updateMemo(memo)
        })
    }

    fun deleteMemo(memo: Memo){
        execute(Runnable {
            AppDataBase.instance.memoDao().deleteMemo(memo)
        })
    }
   private fun execute(runnable: Runnable){
        val thread = Executors.newCachedThreadPool()
        thread.execute { runnable.run() }
    }
    }