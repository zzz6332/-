package com.example.exam.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exam.database.entity.SearchedCity

@Dao
interface SearchedCityDao {

    @Query("SELECT * FROM city")
    fun getAllSearchedCity(): List<SearchedCity>

    @Insert
    fun addSearchedCity(vararg city: SearchedCity)
}