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


class DetailedPostActivity : AppCompatActivity() {
    private val defaulturl = "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4"
    private var playbackPosition = 0
    private var postVideoName: String? = null
    private var context: Context? = null

    private lateinit var pgBar : ProgressBar
    private lateinit var iPostVideoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var url: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detailed_post)

        //fetching intents
        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID", 0)
        val postTitle = intent.getStringExtra("iPostTitle")
        val postDateCreated = intent.getStringExtra("iPostDate")
        val postContent = intent.getStringExtra("iPostContent")
        postVideoName = intent.getStringExtra("iPostVideoName")
        val postMetaData = intent.getStringExtra("iPostMetaData")
        val tvCategory : TextView = findViewById(R.id.tvCategory)
        context = this.applicationContext

        if (postContent != null) {
            url = if (postContent.contains("<video")){
                val initialPositionHttps : Int = postContent.indexOf("https:")
                var finalPosition : Int = postContent.indexOf(".mp4")
                finalPosition += 4
                postContent.substring(initialPositionHttps, finalPosition)
            }else{
                defaulturl
            }

            pgBar = findViewById(R.id.progressBar)
            iPostVideoView=findViewById(R.id.videoViewWPpost)
            mediaController= MediaController(this@DetailedPostActivity)
            val videocontainer : FrameLayout = findViewById(R.id.videoContainer)

            iPostVideoView.setOnPreparedListener(){
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
        val tvPostTitle:TextView = findViewById(R.id.tvTitle)
        val tvPostContent:TextView = findViewById(R.id.tvContent)
        var str = postContent

        if (str != null) {
            str  = str.replace("<[^>]*>".toRegex(), "")
        }
        tvPostTitle.text = postTitle
        if (str != null) {
            if (str.trim().isEmpty()){
                tvPostContent.text="This post does not have a text description"
            }else{
                //srcUrl
            }
        }
    }

    override fun onStart() {
        super.onStart()
        System.out.println(postVideoName)
        val uri = Uri.parse(Environment.DIRECTORY_MOVIES + "/ECD" + "/" + postVideoName)


        System.out.println("Path is "+ this.filesDir.absolutePath)
        iPostVideoView.setVideoURI(uri)
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

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Video.Media._ID)
        val cursor: Cursor? = context!!.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI !!, projection, null, null, null)
        System.out.println("context is "+context)
        return if (cursor != null) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

}