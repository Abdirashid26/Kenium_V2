package com.eclectics.kenium_v2.di.repo

import com.eclectics.kenium_v2.di.local.ArticleDB
import com.eclectics.kenium_v2.di.local.ArticleDao
import com.eclectics.kenium_v2.home.menus.states.SavedState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainDatabaseRepository @Inject constructor(
    private val articleDao: ArticleDao
){

    fun getAllArticlesFromDB() : Flow<List<ArticleDB>> {
        return articleDao.getAllArticlesFromDb()
    }


    suspend fun insertArticle(articleDB: ArticleDB) : SavedState{
       val successNumber =  articleDao.insertArticle(articleDB)

        if (successNumber >= 0){
//            this is successful
            return SavedState.Success
        }else{
            return SavedState.Failed
        }
    }

}