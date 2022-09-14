package com.example.ecd_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.room.Post


/**
 * PostListAdapter drives the changes in the post list. Holds all data related to post item
 */
class PostListAdapter(): ListAdapter<Post, PostListAdapter.PostViewHolder>(Posts_COMPARATOR)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    /**
     * Binding the data for the post list item
     * @param holder post view holder
     * @param position position referenced in the post list
     */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = getItem(position)//gets item at the position index
        holder.bind(current.postTitle, current.postContent, current.metaData) //binding the data for the post to the UI component

        /**
         * Creating the click listener for post item
         */
        holder.itemView.setOnClickListener(){
            //Setting the details for post ui component
            var postID : Int = current.id
            var postTitle : String = current.postTitle
            var postDateCreated : String = current.dateCreated
            var postContent : String = current.postContent
            var postVideoName : String = current.videoName!!
            var postMetaData : String = current.metaData

            //creating intent to transfer data
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
        private val postTitleEdit: TextView = itemView.findViewById(R.id.tvTitle)
        private val postTitleDescriptionEdit: TextView = itemView.findViewById(R.id.tvDescription)
        private val postImageView : ImageView = itemView.findViewById(R.id.ivPostImage)
//        private val postDateEdit: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(postTitle: String?, postDescription: String?, postMetaData: String?) {
            postTitleEdit.text = postTitle
            when{
                postMetaData.equals("Baby Health") -> postImageView.setImageResource(R.drawable.health)
                postMetaData.equals("Baby Development") -> postImageView.setImageResource(R.drawable.baby)
                postMetaData.equals("Parent Health") -> postImageView.setImageResource(R.drawable.parent)
                postMetaData.equals("Assigned Content") -> postImageView.setImageResource(R.drawable.assigned)
            }
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