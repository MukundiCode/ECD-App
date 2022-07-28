package com.example.ecd_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.room.Post
import com.mysql.jdbc.Messages.getString
import kotlin.coroutines.coroutineContext

class PostListAdapter(): ListAdapter<Post, PostListAdapter.PostViewHolder>(Posts_COMPARATOR)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.postTitle, current.postContent, current.dateCreated)
        //holder.itemView.setOnClickListener(){
        //    d("clickFunction", "clicked")
        //click listener shouldnt be too bad
        //djm

        //}

        /**
         * data class Post(@PrimaryKey (autoGenerate = true) val id: Int,
        val postTitle: String,
        val dateCreated: String,
        val postContent: String,
        val metaData: String) {

        side code stuff
        //            Toast.makeText(it.context, "hello", Toast.LENGTH_LONG ).show()
        //            itemClickFunc.invoke()

         */

        holder.itemView.setOnClickListener(){
            //we will refer to current
            var postID : Int = current.id
            var postTitle : String = current.postTitle
            var postDateCreated : String = current.dateCreated
            var postContent : String = current.postContent
            var postMetaData : String = current.metaData

            //creating intent to transfer data

            val intent = Intent(it.context, DetailedPostActivity::class.java)
            //putting data into a intent
            intent.putExtra("iPostID", postID )
            intent.putExtra("iPostTitle", postTitle)
            intent.putExtra("iPostDate", postDateCreated)
            intent.putExtra("iPostContent", postContent)
            intent.putExtra("iPostMetaData", postMetaData)
            it.context.startActivity(intent) //starting a new activity

        }
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val postItemView: TextView = itemView.findViewById(R.id.textView)
        private val postTitleEdit: TextView = itemView.findViewById(R.id.tvTitle)
        private val postTitleDescriptionEdit: TextView = itemView.findViewById(R.id.tvDescription)
//        private val postDateEdit: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(postTitle: String?, postDescription: String?, postDate: String?) {
//            postItemView.text = text
            postTitleEdit.text = postTitle
            //postTitleDescriptionEdit.text = Resources.getSystem().getString(R.string.loremipsum)
//            postDateEdit.text = postDate
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