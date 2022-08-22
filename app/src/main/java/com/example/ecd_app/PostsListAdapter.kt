package com.example.ecd_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.room.Post
import com.mysql.jdbc.Messages.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.coroutineContext

class PostListAdapter(): ListAdapter<Post, PostListAdapter.PostViewHolder>(Posts_COMPARATOR)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.postTitle, current.postContent, current.metaData,current.videoName)
        holder.itemView.setOnClickListener() {
            //we will refer to current
            var postID: Int = current.id
            var postTitle: String = current.postTitle
            var postDateCreated: String = current.dateCreated
            var postContent: String = current.postContent
            var postVideoName: String = current.videoName!!
            var postMetaData: String = current.metaData

            val intent = Intent(it.context, DetailedPostActivity::class.java)
            //putting data into a intent
            intent.putExtra("iPostID", postID )
            intent.putExtra("iPostTitle", postTitle)
            intent.putExtra("iPostDate", postDateCreated)
            intent.putExtra("iPostContent", postContent)
            intent.putExtra("iPostVideoName", postVideoName)
            intent.putExtra("iPostMetaData", postMetaData)
            it.context.startActivity(intent) //starting a new activity
        }
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val postItemView: TextView = itemView.findViewById(R.id.textView)
        private val postTitleEdit: TextView = itemView.findViewById(R.id.tvTitle)
        private val postTitleDescriptionEdit: TextView = itemView.findViewById(R.id.tvDescription)
        private val postImageView : ImageView = itemView.findViewById(R.id.ivPostImage)

        fun bind(postTitle: String?, postDescription: String?, postMetaData: String?,videoName: String?) {
            postTitleEdit.text = postTitle
            when{
                postMetaData.equals("Baby Health") -> postImageView.setImageResource(R.drawable.health)
                postMetaData.equals("Baby Development") -> postImageView.setImageResource(R.drawable.baby)
                postMetaData.equals("Parent Health") -> postImageView.setImageResource(R.drawable.parent)
                postMetaData.equals("Assigned Content") -> postImageView.setImageResource(R.drawable.assigned)
            }
            val deleteButton: Button = itemView.findViewById(R.id.delete_button)
            deleteButton.setOnClickListener {
                var videos = (it.context.applicationContext as ECDApplication).videos

                if (videos != null) {
                    for(v in videos){
                        if (v.VIDEO_NAME == videoName){
                            //delete
                            var file = File(v.VIDEO_PATH)
                            file.delete()
                        }
                    }
                }

                var postsRepository = (it.context.applicationContext as ECDApplication).repository
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    if (postTitle != null) {
                        postsRepository.deletebyName(postTitle)
                    }
                }
            }
//            postTitleDescriptionEdit.text = postDescription
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