package com.eclectics.kenium_v2.di.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Query("select * from articles_saved")
    fun getAllArticlesFromDb() : Flow<List<ArticleDB>>


    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(artcileDB: ArticleDB) : Long


}