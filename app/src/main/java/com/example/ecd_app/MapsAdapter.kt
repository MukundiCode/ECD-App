package com.example.ecd_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.mapModels.UserMap


/**
 * @author Suvanth Ramruthen
 * MapAdapter drives the changes in the Map recyclerView. Holds all data related to map item
 * @property context the context passed to the activity
 * @property userMaps the list of maps to be displayed
 * @property onClickListener map onClickListener
 */
class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Map Item click listener
     */
    interface OnClickListener{
        fun onItemClick(position: Int){
        }
    }


    /**
     * Ceate method for map list item being created
     * @param parent parent view that the item is displayed in
     * @param viewType type of view being displayed
     * @return viewholder for recyclerView is returned
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutView = LayoutInflater.from((parent.context)).inflate(com.example.ecd_app.R.layout.map_item_layout, parent, false)
        return ViewHolder(layoutView)
    }

    /**
     * Binding data for the view that is being created for map item
     * @param holder reference to item holder
     * @param position position in map list
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvMapTitle= holder.itemView.findViewById<TextView>(com.example.ecd_app.R.id.tvMapTitle)//view reference for title
        val userMap = userMaps[position]//reference map in array
        tvMapTitle.text = userMap._title//setting title
        holder.itemView.setOnClickListener(){
            Toast.makeText(context,"You clicked ${tvMapTitle.text}", Toast.LENGTH_SHORT).show()//Toast showing map title
            onClickListener.onItemClick(position)//calling click listener with position received as argument
        }
    }

    /**
     * Getting the amount of userMaps stored
     * @return Maps List size
     */
    override fun getItemCount(): Int {
        return userMaps.size
    }

    /**
     * Constructor for viewHolder
     * @constructor Creates and inbitailizes ViewHolder for RecyclerView
     * @param itemView map item list
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}
