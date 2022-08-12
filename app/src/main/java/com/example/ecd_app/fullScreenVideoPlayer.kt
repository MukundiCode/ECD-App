package com.example.ecd_app

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class fullScreenVideoPlayer : AppCompatActivity() {

    var isFullScreen = false
    lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_video_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "exo"


        val playerView = findViewById<PlayerView>(R.id.player)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
//        val bt_fullscreen = findViewById<ImageView>(R.id.bt_fullscreen)
//        val bt_lockscreen = findViewById<ImageView>(R.id.exo_lock)
        var intent = intent //getting the intent
        val postVideoPath = intent.getStringExtra("VIDEOLINK")

//        bt_fullscreen.setOnClickListener(){
//            if(!isFullScreen){
//                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fullscreen_exit))
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//            }else{
//                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fullscreen))
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//
//            }
//            isFullScreen =!isFullScreen
//
//        }



         simpleExoPlayer= SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()
        playerView.player = simpleExoPlayer
        playerView.keepScreenOn=true

        simpleExoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState==Player.STATE_BUFFERING){
                    progressBar.visibility= View.VISIBLE

                }
                else if(playbackState==Player.STATE_READY){
                    progressBar.visibility=View.GONE
                }
            }

        })

//        val videoSource = Uri.parse("https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4")
        val videoSource = Uri.parse(postVideoPath)
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()


    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

//    override fun onPause() {
//        super.onPause()
//        simpleExoPlayer.release()
//    }

}