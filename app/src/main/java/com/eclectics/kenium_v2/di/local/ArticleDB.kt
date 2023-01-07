package com.eclectics.kenium_v2.di.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_saved")
data class ArticleDB(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val author : String,
    val headline : String,
    val image : String,
    val likes : Int,
    val summary : String
)
