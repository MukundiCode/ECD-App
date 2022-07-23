package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailedPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_post)
        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID",0)
        val postTitle = intent.getStringExtra("iPostTitle")
        val postDateCreated = intent.getStringExtra("iPostDate")
        val postContent = intent.getStringExtra("iPostContent")
        val postMetaData = intent.getStringExtra("iPostMetaData")

//        intent.putExtra("iPostID", postID )
//        intent.putExtra("iPostTitle", postTitle)
//        intent.putExtra("iPostDate", postDateCreated)
//        intent.putExtra("iPostContent", postContent)
//        intent.putExtra("iPostMetaData", postMetaData)

        val tvPostId:TextView = findViewById(R.id.tvPostId)
        val tvPostTitle:TextView = findViewById(R.id.tvPostTitle)
        val tvPostDate:TextView = findViewById(R.id.tvPostDate)
        val tvPostContent:TextView = findViewById(R.id.tvPostContent)
        val tvPostMetaData:TextView = findViewById(R.id.tvPostMetaData)

        tvPostId.text = postID.toString()
        tvPostTitle.text = postTitle
        tvPostDate.text = postDateCreated
        tvPostContent.text = postContent
        tvPostMetaData.text = postMetaData






    }
}