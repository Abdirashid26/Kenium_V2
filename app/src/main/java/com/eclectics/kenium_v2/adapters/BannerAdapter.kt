package com.eclectics.kenium_v2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eclectics.kenium_v2.R
import com.example.kenium.model.Banner

class BannerAdapter(val context: Context,val bannerList: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var heading = itemView.findViewById<TextView>(R.id.heading)
        var summary = itemView.findViewById<TextView>(R.id.summary)
        var image  = itemView.findViewById<ImageView>(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(context).inflate(R.layout.banner_item,parent,false)
        return  ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.heading.text = bannerList.get(position).heading
        holder.summary.text = bannerList.get(position).summary
        Glide.with(context).load(bannerList.get(position).image).centerCrop().into(holder.image)

    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

}