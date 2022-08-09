package com.example.ecd_app

import android.content.Context
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup

class DetailedPostActivity : AppCompatActivity() {
    private val defaulturl = "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4"
    private var playbackPosition = 0
    private lateinit var pgBar: ProgressBar
    private lateinit var iPostVideoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var url: String
    private var postVideoName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_detailed_post)


        //fetching intents
        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID", 0)
        val postTitle = intent.getStringExtra("iPostTitle")
        supportActionBar?.title = postTitle
        val postDateCreated = intent.getStringExtra("iPostDate")
        val postContent = intent.getStringExtra("iPostContent")
        val postMetaData = intent.getStringExtra("iPostMetaData")
        Toast.makeText(this@DetailedPostActivity, postMetaData, Toast.LENGTH_LONG).show()
        postVideoName = intent.getStringExtra("iPostVideoName")
        //getting elements
        val tvCategory: TextView = findViewById(R.id.tvCategory)
        val tvPostTitle: TextView = findViewById(R.id.tvTitle)
        val tvPostContent: TextView = findViewById(R.id.tvContent)


        //clean up the wp postContent html jsoup
        val doc: org.jsoup.nodes.Document? = Jsoup.parse(postContent)
        val text: String? = doc?.text()
        tvPostTitle.text=postTitle
        tvPostContent.text = text
        if (tvPostContent.text.trim().isEmpty()) {
            tvPostContent.text = "No text decription in this post"

        }
        val element = doc?.select("video")
        val srcUrl = element?.attr("src")
        if (srcUrl != null) {
            url = if (srcUrl.trim().isEmpty()){
                defaulturl
            }else{
                srcUrl
            }
        }

        //category
        when {
            postTitle?.lowercase()?.contains("food") == true && postTitle.lowercase()
                .contains("breast") -> tvCategory.text = "Nutrition"
            postTitle?.lowercase()?.contains("breast") == true -> tvCategory.text = "Breastfeeding"
            else -> tvCategory.text = "General"
        }

        //media controller
        pgBar = findViewById(R.id.progressBar)
        iPostVideoView = findViewById(R.id.videoViewWPpost)
        mediaController = MediaController(this@DetailedPostActivity)
        val videocontainer: FrameLayout = findViewById(R.id.videoContainer)

        iPostVideoView.setOnPreparedListener() {
            mediaController.setAnchorView(videocontainer)
            iPostVideoView.setMediaController(mediaController)
            iPostVideoView.seekTo(playbackPosition)
            iPostVideoView.start()
        }

        iPostVideoView.setOnInfoListener{ player, what, extras ->
            if(what== MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                pgBar.visibility = View.INVISIBLE
            };true
        }
    }
    override fun onStart() {
        super.onStart()
        System.out.println(postVideoName)
        var videos = this?.let { fetchVideos(it.contentResolver) }
        if (videos != null) {
            var vids = videos.blockingGet()
            for (v in vids){
                if (v.VIDEO_NAME == postVideoName){
                    iPostVideoView.setVideoURI(Uri.parse(v.VIDEO_PATH))
                    break
                }
            }
        }
        pgBar.visibility= View.VISIBLE


    }

    override fun onPause() {
        iPostVideoView.pause()
        playbackPosition = iPostVideoView.currentPosition
        super.onPause()
    }

    override fun onStop() {
        iPostVideoView.stopPlayback()
        super.onStop()
    }

}