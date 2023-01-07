package com.eclectics.kenium_v2.home.menus.states

sealed class SavedState {

    object Success : SavedState()
    object Failed : SavedState()

}