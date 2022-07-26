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

        val tvPostTitle:TextView = findViewById(R.id.tvTitle)
        val tvPostContent:TextView = findViewById(R.id.tvContent)

        tvPostTitle.text = postTitle
        tvPostContent.text = postContent

    }
}