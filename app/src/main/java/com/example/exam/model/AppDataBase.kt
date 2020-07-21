package com.example.exam.model

import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exam.App
import com.example.exam.database.dao.MemoDao
import com.example.exam.database.dao.SearchedCityDao
import com.example.exam.database.entity.Memo
import com.example.exam.database.entity.SearchedCity

@Database(entities = [Memo::class,SearchedCity::class], version = 4, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    abstract fun searchedCityDao(): SearchedCityDao

    companion object {
        val instance: AppDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(App.context, AppDataBase::class.java, "app1").build()
        }
    }
}
