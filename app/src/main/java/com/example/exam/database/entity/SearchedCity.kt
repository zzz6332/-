package com.example.exam.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
class SearchedCity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var cityId: String,
    var cityName: String
) {
}