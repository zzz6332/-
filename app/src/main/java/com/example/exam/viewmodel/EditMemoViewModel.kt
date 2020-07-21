package com.example.exam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exam.database.entity.Memo
import com.example.exam.repository.MemoRepository

class EditMemoViewModel: ViewModel() {
    private val repository = MemoRepository()

    fun addMemo(memo: Memo){
        repository.addMemoData(memo)
    }

    fun updataMemo(memo: Memo){
        repository.updateMemo(memo)
    }
    fun deleteMemo(memo: Memo){
        repository.deleteMemo(memo)
    }
}