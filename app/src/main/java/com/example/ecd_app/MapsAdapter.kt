package com.example.ecd_app

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.mapModels.UserMap


class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener{
        fun onItemClick(position: Int){
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutView = LayoutInflater.from((parent.context)).inflate(com.example.ecd_app.R.layout.map_item_layout, parent, false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvMapTitle= holder.itemView.findViewById<TextView>(com.example.ecd_app.R.id.tvMapTitle)
        val userMap = userMaps[position]
        tvMapTitle.text = userMap._title
        holder.itemView.setOnClickListener(){
            Toast.makeText(context,"You clicked ${tvMapTitle.text}", Toast.LENGTH_SHORT).show()
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}
