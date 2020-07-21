package com.example.exam.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exam.bean.MemoType

@Entity(tableName = "memo")
class Memo( @PrimaryKey(autoGenerate = true) var id:Int,
            var title: String,
            var content: String,
            var time: Long,
            var finish: Boolean,
            var type: Int = MemoType.NORMAL){
}