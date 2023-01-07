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
import com.eclectics.kenium_v2.model.Item

class ItemAdapter (val context: Context, val itemList : MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image = itemView.findViewById<ImageView>(R.id.image)
        var name = itemView.findViewById<TextView>(R.id.name)
        var price = itemView.findViewById<TextView>(R.id.price)
        var contact = itemView.findViewById<TextView>(R.id.contact)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.sell_item, parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(itemList.get(position).image).into(holder.image)
        holder.name.text = itemList.get(position).name
        holder.price.text = "Price :" + itemList.get(position).price
        holder.contact.text = itemList.get(position).contact

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
