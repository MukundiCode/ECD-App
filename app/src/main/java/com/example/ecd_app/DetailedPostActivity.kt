package com.example.ecd_app

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup

/**
 * @author Suvanth Ramruthen
 * DetailedPostActivity renders WP posts in a user interpretable format.
 */
class DetailedPostActivity : AppCompatActivity() {
    private val defaulturl =
        "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4"
    private lateinit var url: String
    private var postVideoName: String? = null


    /**
     * OnCreate method is called on activity launch, setups activity with post details
     * @param savedInstanceState saved bundle instance
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//enabling back button
        setContentView(R.layout.activity_detailed_post)//inflating detailed post xml file
        val videoButtonImageView: ImageView = findViewById(R.id.videoImageButton)//finding videoButton

        /**
         * Creating button listener for video image button launch
         */
        videoButtonImageView.setOnClickListener() {
            val intentVid = Intent(this@DetailedPostActivity, fullScreenVideoPlayer::class.java)//create intent
            var videos = this?.let { fetchVideos(it.contentResolver) }
            var found = false
            if (videos != null) {
                var vids = videos.blockingGet()
                for (v in vids) {
                    if (v.VIDEO_NAME == postVideoName) {
                        intentVid.putExtra("VIDEOLINK", v.VIDEO_PATH)//putting video link in intent
                        startActivity(intentVid)
                        found = true
                        break
                    }
                }
                if (!found){
                    Toast.makeText(this, "Video does not exist", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //fetching intents
        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID", 0)
        val postTitle = intent.getStringExtra("iPostTitle")
        supportActionBar?.title = postTitle
        val postDateCreated = intent.getStringExtra("iPostDate")
        val postContent = intent.getStringExtra("iPostContent")
        val postMetaData = intent.getStringExtra("iPostMetaData")
        postVideoName = intent.getStringExtra("iPostVideoName")
        //getting elements
        val tvCategory: TextView = findViewById(R.id.tvCategory)
        val tvPostTitle: TextView = findViewById(R.id.tvTitle)
        val tvPostContent: TextView = findViewById(R.id.tvContent)
        tvCategory.text = postMetaData

        //clean up the wp postContent html jsoup
        val doc: org.jsoup.nodes.Document? = Jsoup.parse(postContent)//creating document object for jSoup manipulation
        val text: String? = doc?.text()//getting text without xml tags
        tvPostTitle.text = postTitle
        tvPostContent.text = text
        if (tvPostContent.text.trim().isEmpty()) {
            tvPostContent.text = "No text decription in this post" //diplaying no text

        }
        val element = doc?.select("video")//looking for video link in jSoup doc
        val srcUrl = element?.attr("src")// filtering by src tag
        if (srcUrl != null) {
            url = if (srcUrl.trim().isEmpty()) {
                defaulturl
            } else {
                srcUrl
            }
        }


    }
}

