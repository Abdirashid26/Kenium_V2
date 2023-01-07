package com.eclectics.kenium_v2.auth.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.databinding.FragmentLoginBinding
import com.eclectics.kenium_v2.dialogs.FailedDialog
import com.eclectics.kenium_v2.dialogs.ProgressDialog
import com.eclectics.kenium_v2.model.UserSession
import com.eclectics.kenium_v2.repo.FirebaseRepo
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()




    private val progressDialog = ProgressDialog("Authenticating User")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = FirebaseRepo().firebaseAuth.currentUser

        if (currentUser != null){
            UserSession.userName = currentUser.displayName.toString()
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        loginStateObserver()

        binding.loginBtn.setOnClickListener {
            loginIntoKenium()
        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return binding.root
    }


    private fun loginIntoKenium(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (isValidEmail(email) && isValidInput(password)){
            loginViewModel.login(email,password)
        }else{
            Snackbar.make(binding.root,"Please Enter a Valid Email / Password",Snackbar.LENGTH_SHORT).show()
        }

    }


    private fun loginStateObserver(){
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginState.collectLatest {
                when(it){
                    is LoginState.Loading -> {
                        progressDialog.show(
                            childFragmentManager,
                            progressDialog.tag
                        )
                    }
                    is LoginState.Failed -> {
                        progressDialog.dismiss()
                        FailedDialog(
                            "Login",
                            "Login Failed"
                        ).show(
                            childFragmentManager,
                            FailedDialog.TAG
                        )
                    }
                    is LoginState.Success -> {
                        progressDialog.dismiss()
//                        navigate to Home Fragment
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    fun isValidInput(password : String) : Boolean{
        return password.isNotEmpty()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}