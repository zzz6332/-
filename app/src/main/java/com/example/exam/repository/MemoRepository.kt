package com.example.exam.repository

import com.example.exam.database.entity.Memo
import com.example.exam.model.MemoModel

class MemoRepository {

    private val model: MemoModel = MemoModel()

    fun getMemoData(l:(list:List<Memo>) -> Unit){
        model.getMemoData(l)
    }

    fun addMemoData(memo: Memo) = model.addMemoData(memo)

    fun updateMemo(memo: Memo){
         model.updateMemo(memo)
    }

    fun deleteMemo(memo: Memo){
         model.deleteMemo(memo)
    }
}