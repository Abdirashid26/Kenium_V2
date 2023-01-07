package com.eclectics.kenium_v2.app

import android.content.Context
import androidx.room.Room
import com.eclectics.kenium_v2.di.local.ArticleDao
import com.eclectics.kenium_v2.di.local.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            appContext,
            ArticlesDatabase::class.java,
            "Kenium_local_storage"
        ).build()
    }



    @Provides
    @Singleton
    fun providesArticlesDao(articlesDatabase: ArticlesDatabase) : ArticleDao{
        return  articlesDatabase.articleDao()
    }

}