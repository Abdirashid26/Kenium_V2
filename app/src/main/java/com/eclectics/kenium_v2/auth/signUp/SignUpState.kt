package com.eclectics.kenium_v2.auth.signUp

sealed class SignUpState(){
    data class  Failed(val errorMessage : String) : SignUpState()
    object Success : SignUpState()
    object Loading : SignUpState()
}
