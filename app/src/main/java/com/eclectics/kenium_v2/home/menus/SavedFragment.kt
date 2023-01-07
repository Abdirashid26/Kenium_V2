package com.eclectics.kenium_v2.home.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.adapters.ArticleAdapter
import com.eclectics.kenium_v2.adapters.ArticleDbAdapter
import com.eclectics.kenium_v2.di.local.ArticleDB
import com.eclectics.kenium_v2.model.Article
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SavedFragment : Fragment() {

    private  var fullList: MutableList<ArticleDB> = mutableListOf()
    private var mFirestore = FirebaseFirestore.getInstance()
    private lateinit var artcileAdapter: ArticleDbAdapter
    private lateinit var rvFullList: RecyclerView


    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_saved, container, false)

        artcileAdapter = ArticleDbAdapter(requireContext(),fullList) { article ->
            showArticle(article)
        }
        rvFullList =  v.findViewById(R.id.fullList)


        getArticledFromDb()


        rvFullList.adapter = artcileAdapter
        rvFullList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        return v
    }



    private fun getArticledFromDb(){
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                mainViewModel.allArticlesFromDb.collectLatest {
                    rvFullList.adapter = ArticleDbAdapter(requireContext(),it.toMutableList()){
                        showArticle(it)
                    }
                }
        }
    }



    private fun showArticle(article: ArticleDB){
        val bundle = bundleOf()
        val newArticle = Article(
            article.headline,
            article.summary,
            article.image,
            article.likes,
            "",
            article.author
        )
        bundle.putSerializable("article",newArticle)
        findNavController().navigate(
            R.id.action_savedFragment_to_articleFragment,
            bundle
        )
    }

}