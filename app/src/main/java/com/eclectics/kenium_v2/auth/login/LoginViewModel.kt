package com.eclectics.kenium_v2.auth.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclectics.kenium_v2.auth.signUp.SignUpState
import com.eclectics.kenium_v2.model.UserSession
import com.eclectics.kenium_v2.repo.FirebaseRepo
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepo: FirebaseRepo
) : ViewModel() {

    private val _loginState : MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginState : SharedFlow<LoginState> = _loginState.asSharedFlow()

    private val _signUpState : MutableSharedFlow<SignUpState> = MutableSharedFlow()
    val signUpState  : SharedFlow<SignUpState> = _signUpState.asSharedFlow()


    fun login(email: String, password: String){
        viewModelScope.launch {
            _loginState.emit(LoginState.Loading)
        }
        firebaseRepo.firebaseAuth.signInWithEmailAndPassword(email,
            password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful){
                viewModelScope.launch {
//                    UserSession.userName = firebaseRepo.firebaseAuth.currentUser?.displayName!!
                    firebaseRepo.firebaseAuth.currentUser?.let {
                        Log.i("DISPLAY_NAME",it.displayName.toString())
                        UserSession.userName = it.displayName.toString()
                    }
                    _loginState.emit(LoginState.Success)
                }
            }
            else if(task.isCanceled){
                viewModelScope.launch {
                    _loginState.emit(LoginState.Failed("Login Failed"))
                }
            }
            else{
                viewModelScope.launch {
                    _loginState.emit(LoginState.Failed("Authentication Failed"))
                }
            }
        }

    }

    fun signUp(email: String,password: String, username : String){
        viewModelScope.launch {
            _signUpState.emit(SignUpState.Loading)
        }
        firebaseRepo.firebaseAuth.createUserWithEmailAndPassword(email,
            password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful){

//                    firebaseRepo.firebaseAuth.
                    val user = firebaseRepo.firebaseAuth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener {task ->
                            viewModelScope.launch {
                                if (task.isSuccessful) {
                                    _signUpState.emit(SignUpState.Success)
                                }else{
                                    _signUpState.emit(SignUpState.Failed(task.exception.toString()))
                                }

                            }
                        }


            }
            else if(task.isCanceled){
                viewModelScope.launch {
                    _signUpState.emit(SignUpState.Failed("Sign Up Failed"))
                }
            }
            else{
                viewModelScope.launch {
                    _signUpState.emit(SignUpState.Failed(task.exception.toString()))
                }
            }
        }
    }

}