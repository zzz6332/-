package com.example.exam.database.dao

import androidx.room.*
import com.example.exam.database.entity.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo")
    fun getMemo(): List<Memo>

    @Delete
    fun deleteMemo(vararg memo: Memo)

    @Update
    fun updateMemo(vararg memo: Memo)

    @Insert
    fun addMemo(vararg memo: Memo)
}