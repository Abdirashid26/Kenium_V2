package com.eclectics.kenium_v2.di.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleDB::class], version = 1)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract  fun articleDao(): ArticleDao
}