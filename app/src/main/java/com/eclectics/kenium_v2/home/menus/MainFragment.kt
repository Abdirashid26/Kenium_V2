package com.eclectics.kenium_v2.home.menus

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.adapters.ArticleAdapter
import com.eclectics.kenium_v2.adapters.BannerAdapter
import com.eclectics.kenium_v2.model.Article
import com.example.kenium.model.Banner
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {


    private lateinit var banners: RecyclerView
    private lateinit var myContent: RecyclerView
    private lateinit var myChips: ChipGroup
    private lateinit var filterText: TextView
    private lateinit var cs: Chip
    private lateinit var bs: Chip
    private lateinit var sp: Chip
    private lateinit var ns: Chip
    private val mFirestore  : FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var  artcileAdapter: ArticleAdapter


    private var bannerList : ArrayList<Banner> = ArrayList()
    private var contentsList : MutableList<Article> = mutableListOf<Article>()
    private var chipsList : ArrayList<Chip> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  v = inflater.inflate(R.layout.fragment_main, container, false)


        filterText = v.findViewById(R.id.filterText)
//        initalize the Recycler view
        banners = v.findViewById(R.id.banners)
        myContent = v.findViewById(R.id.myContent)

        myChips = v.findViewById(R.id.chipGroup)
        cs = v.findViewById(R.id.cs)
        bs = v.findViewById(R.id.bs)
        ns = v.findViewById(R.id.ns)
        sp = v.findViewById(R.id.sp)

        artcileAdapter  = ArticleAdapter(requireContext(),contentsList) { article ->
            showArticle(article)
        }

        myContent.adapter = artcileAdapter

        myContent.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)



        chipsList.add(bs)
        chipsList.add(ns)
        chipsList.add(sp)
        chipsList.add(cs)




        chipsList.forEach {
            var text = it.text.toString()
            it.chipBackgroundColor = setChipBackgroundColor(
                checkedColor = Color.BLACK,
                unCheckedColor = Color.GRAY
            )
            it.isChecked = true
            it.setOnClickListener {
                filterText.text = text
                lifecycleScope.launch(Dispatchers.IO){
                    getArtciles(it.id)

                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO){
            bannerList.add(Banner("Computer Science","Transforming the world through computing and innovation",R.drawable.cs))
            bannerList.add(Banner("Medicine","Curing the world, one patient at a time",R.drawable.ns))
            bannerList.add(Banner("Business","Excelling in the world of finance and management",R.drawable.bs2))
            bannerList.add(Banner("Sports","Achieving physical excellence through determination and hard work",R.drawable.sp3))
            getArtciles(R.id.cs)

        }

        lifecycleScope.launch(Dispatchers.IO){
            banners.adapter = BannerAdapter(requireContext(),bannerList)

        }

        banners.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)



        return v
    }





    private fun showArticle(article: Article){
        val bundle = bundleOf()
        bundle.putSerializable("article",article)
        findNavController().navigate(
            R.id.action_mainFragment_to_articleFragment,
            bundle
        )
    }


    suspend fun getArtciles(artcileId :Int){
        if(artcileId == R.id.cs){
            mFirestore.collection("csArticles").addSnapshotListener { value, error ->
                if(value != null && error == null){
                    contentsList.clear()
                    var artciles = value.toObjects(Article::class.java)
                    contentsList.addAll(artciles)
                    artcileAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if(artcileId == R.id.bs){
            mFirestore.collection("bsArticles").addSnapshotListener { value, error ->
                if(value != null && error == null){
                    contentsList.clear()
                    var artciles = value.toObjects(Article::class.java)
                    contentsList.addAll(artciles)
                    artcileAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if(artcileId == R.id.ns){
            mFirestore.collection("nsArticles").addSnapshotListener { value, error ->
                if(value != null && error == null){
                    contentsList.clear()
                    var artciles = value.toObjects(Article::class.java)
                    contentsList.addAll(artciles)
                    artcileAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        else if(artcileId == R.id.sp){
            mFirestore.collection("spArticles").addSnapshotListener { value, error ->
                if(value != null && error == null){
                    contentsList.clear()
                    var artciles = value.toObjects(Article::class.java)
                    contentsList.addAll(artciles)
                    artcileAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(requireContext(),"Error : " + error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun setChipBackgroundColor(
        checkedColor: Int = Color.BLACK,
        unCheckedColor: Int = Color.GRAY
    ): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )

        val colors = intArrayOf(
            // chip checked background color
            checkedColor,
            // chip unchecked background color
            unCheckedColor
        )

        return ColorStateList(states, colors)
    }


}