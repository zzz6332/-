package com.example.exam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exam.database.entity.Memo
import com.example.exam.repository.MemoRepository

class MemoViewModel : ViewModel() {
    var list: MutableLiveData<List<Memo>> = MutableLiveData()
    private val repository: MemoRepository = MemoRepository()
    fun getMemoData(){
        repository.getMemoData {
            val data = it
            list.postValue(data)
        }
    }

}