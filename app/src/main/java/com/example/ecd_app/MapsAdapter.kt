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
        val view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userMap = userMaps[position]
        val tvTitle = holder.itemView.findViewById<TextView>(R.id.text1)
        tvTitle.text = userMap._title
        holder.itemView.setOnClickListener(){
            Toast.makeText(context,"You clicked ${tvTitle.text}", Toast.LENGTH_SHORT).show()
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}
