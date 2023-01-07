package com.eclectics.kenium_v2.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.databinding.FragmentArticleBinding
import com.eclectics.kenium_v2.di.local.ArticleDB
import com.eclectics.kenium_v2.dialogs.FailedDialog
import com.eclectics.kenium_v2.dialogs.ProgressDialog
import com.eclectics.kenium_v2.home.menus.MainViewModel
import com.eclectics.kenium_v2.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * Author - Faisal Abdirashid
 */

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    lateinit var binding : FragmentArticleBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val progressDialog = ProgressDialog("Saving Article")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleBinding.inflate(layoutInflater)

        val article  = arguments?.getSerializable("article") as Article

        if (article != null){
            binding.headline.text = article.headline
            Glide
                .with(this)
                .load(article.image)
                .placeholder(R.drawable.kenium)
                .into(binding.articleImage);
            binding.summary.text = article.summary
        }

        savedStateObserver()

//        make sure to setup the adapter

        binding.toolBar.setNavigationOnClickListener {
            goBack()
        }

        binding.saveArticleBtn.setOnClickListener {
            progressDialog.show(
                childFragmentManager,
                progressDialog.tag
            )
            val articleDB = ArticleDB(
               0,
                article.author,
                article.headline,
                article.image,
                article.likes,
                article.summary
            )
            insertArticle(articleDB)
        }

        return binding.root
    }


    private fun goBack(){
        findNavController().popBackStack()
    }


    private fun savedStateObserver(){
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mainViewModel.savedState.collectLatest {
                when(it){
                    is com.eclectics.kenium_v2.home.menus.states.SavedState.Success -> {
                        progressDialog.dismiss()
                        FailedDialog(
                            "Downloaded Articles !",
                            "Article Successfully Downloaded !"
                        ).show(
                            childFragmentManager,
                            FailedDialog.TAG
                        )
                    }

                    is com.eclectics.kenium_v2.home.menus.states.SavedState.Failed -> {
                        progressDialog.dismiss()
                        FailedDialog(
                            "Downloaded Articles !",
                            "Download  Failed !"
                        ).show(
                            childFragmentManager,
                            FailedDialog.TAG
                        )
                    }
                }
            }
        }
    }


    private fun insertArticle(articleDB: ArticleDB){
            mainViewModel.saveArticle(articleDB)
    }

}