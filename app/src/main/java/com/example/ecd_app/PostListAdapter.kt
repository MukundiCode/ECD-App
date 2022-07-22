package com.example.ecd_app

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.room.Post

class PostListAdapter: ListAdapter<Post, PostListAdapter.PostViewHolder>(Posts_COMPARATOR)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.postTitle)
        //holder.itemView.setOnClickListener(){
        //    d("clickFunction", "clicked")
        //click listener shouldnt be too bad
        //djm

        //}
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postItemView: TextView = itemView.findViewById(R.id.textView)


        fun bind(text: String?) {
            postItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PostViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return PostViewHolder(view)
            }
        }
}
    companion object {
        //from what ive read online this converts the old outdated list into the new list Transforms
        private val Posts_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.postTitle == newItem.postTitle
            }
        }
    }
}