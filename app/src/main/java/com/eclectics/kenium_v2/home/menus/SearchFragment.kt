package com.eclectics.kenium_v2.home.menus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.adapters.ArticleAdapter
import com.eclectics.kenium_v2.model.Article
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private  var fullList: MutableList<Article> = mutableListOf()
    private var mFirestore = FirebaseFirestore.getInstance()
    private lateinit var artcileAdapter: ArticleAdapter
    private lateinit var rvFullList: RecyclerView
    private lateinit var searchView: SearchView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search, container, false)
            artcileAdapter = ArticleAdapter(requireContext(),fullList) { article ->
                showArticle(article)
            }
        rvFullList =  v.findViewById(R.id.fullList)
        searchView = v.findViewById(R.id.searchView)


        rvFullList.adapter = artcileAdapter
        rvFullList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

//        check if the searchView has a focus before runnnig the on text Listener / watcher

        if(searchView.hasFocus()){
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // on below line we are checking
                    // if query exist or not.
                    search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // if query text is change in that case we
                    // are filtering our adapter with
                    // new text on below line.
                    search(newText)
                    return true
                }
            })
        }



        lifecycleScope.launch(Dispatchers.IO){
            getAllArticles()
        }
        return v
    }





    private fun showArticle(article: Article){
        val bundle = bundleOf()
        bundle.putSerializable("article",article)
        findNavController().navigate(
            R.id.action_searchFragment_to_articleFragment,
            bundle
        )
    }

    private fun search(query: String?) {
        var matchedArticle : ArrayList<Article> = arrayListOf()

        query?.let {
            fullList.forEach { article ->
                if (article.headline.contains(query, true) ||
                    article.summary.contains(query, true)
                ) {
                    matchedArticle.add(article)
                }
            }
            updateRecyclerView(matchedArticle)
            if (matchedArticle.isEmpty()) {
                Toast.makeText(requireContext(), "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView(matchedArticle)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        lifecycleScope.launch(Dispatchers.IO){
//            getAllArticles()
//        }
//    }

    private fun updateRecyclerView(matchedArticle: MutableList<Article>) {

        artcileAdapter = ArticleAdapter(requireContext(),matchedArticle) { article ->
            showArticle(article)
        }
        rvFullList.adapter = artcileAdapter
        artcileAdapter.notifyDataSetChanged()
    }


    private fun getAllArticles(){
        mFirestore.collection("csArticles").addSnapshotListener { value, error ->
            if(value != null && error == null){
                var artciles = value.toObjects(Article::class.java)
                fullList.addAll(artciles)
                artcileAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
            }
        }

        mFirestore.collection("bsArticles").addSnapshotListener { value, error ->
            if(value != null && error == null){
                var artciles = value.toObjects(Article::class.java)
                fullList.addAll(artciles)
                artcileAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
            }
        }

        mFirestore.collection("nsArticles").addSnapshotListener { value, error ->
            if(value != null && error == null){
                var artciles = value.toObjects(Article::class.java)
                fullList.addAll(artciles)
                artcileAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
            }
        }


        mFirestore.collection("spArticles").addSnapshotListener { value, error ->
            if(value != null && error == null){
                var artciles = value.toObjects(Article::class.java)
                fullList.addAll(artciles)
                artcileAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
            }
        }

    }


}