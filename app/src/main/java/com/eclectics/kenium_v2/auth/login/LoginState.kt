package com.eclectics.kenium_v2.auth.login

sealed class LoginState(){
    object Loading : LoginState()
    object Success : LoginState()
    data class Failed(val errorMessage : String) : LoginState()
}
