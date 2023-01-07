package com.eclectics.kenium_v2.auth.signUp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.auth.login.LoginViewModel
import com.eclectics.kenium_v2.databinding.FragmentSignupBinding
import com.eclectics.kenium_v2.dialogs.FailedDialog
import com.eclectics.kenium_v2.dialogs.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {

    lateinit var binding : FragmentSignupBinding


    private val loginViewModel: LoginViewModel by viewModels()

    private val progressDialog = ProgressDialog("Registering User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignupBinding.inflate(layoutInflater)
        signUpStateobserver()
        binding.signUpBtn.setOnClickListener {
            signUp()
        }

        return binding.root
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun signUp(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val username = binding.etUsername.text.toString()

        if(isValidEmail(email) && isValidInput(password) && isValidInput(username)){
            loginViewModel.signUp(email,password, username)
        }
        else{
            Snackbar.make(binding.root,"Please Enter a Valid Email / Username / Password", Snackbar.LENGTH_SHORT).show()
        }

    }


    private fun signUpStateobserver(){
        lifecycleScope.launchWhenStarted {
            loginViewModel.signUpState.collectLatest {
                when(it){
                    is SignUpState.Loading -> {
                        progressDialog.show(
                            childFragmentManager,
                            ProgressDialog.TAG
                        )
                    }
                    is SignUpState.Success -> {
                        progressDialog.dismiss()
                        findNavController().popBackStack()
                    }

                    is SignUpState.Failed -> {
                        progressDialog.dismiss()
                        FailedDialog("Sign Up",it.errorMessage).show(
                            childFragmentManager,
                            FailedDialog.TAG
                        )
                    }
                }
            }
        }
    }


    fun isValidInput(password : String) : Boolean{
        return password.isNotEmpty()
    }




}