package com.eclectics.kenium_v2.home.menus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclectics.kenium_v2.di.local.ArticleDB
import com.eclectics.kenium_v2.di.repo.MainDatabaseRepository
import com.eclectics.kenium_v2.home.menus.states.SavedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainDatabaseRepository: MainDatabaseRepository
) : ViewModel() {


    private val _savedState : MutableSharedFlow<SavedState> = MutableSharedFlow()
    val savedState : SharedFlow<SavedState> = _savedState.asSharedFlow()

     val allArticlesFromDb  = mainDatabaseRepository.getAllArticlesFromDB()

    fun saveArticle(articleDB: ArticleDB){
        try{
            viewModelScope.launch(Dispatchers.IO){
               _savedState.emit(mainDatabaseRepository.insertArticle(articleDB))
            }
        }catch(t : Throwable){
            viewModelScope.launch(Dispatchers.IO){
                _savedState.emit(SavedState.Failed)
            }
        }
    }



}