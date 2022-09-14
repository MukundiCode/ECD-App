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

/**
 * @author Suvanth Ramruthen
 * fulLScreenVideoPlayer leverages Google Exoplayer to display ecd content in a video player that leverages affordances of similar streaming multimedia platforms
 */
class fullScreenVideoPlayer : AppCompatActivity() {

    var isFullScreen = false //fullscreen boolean status
    lateinit var simpleExoPlayer: SimpleExoPlayer//exoplayer view to be initialised

    /**
     * onCreate sets up the activity for playing media files
     * @param savedInstanceState saved bundle instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_video_player) //inflating layout for media player
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//back button enabled
        supportActionBar?.title = "exo"


        //views for ui component
        val playerView = findViewById<PlayerView>(R.id.player)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val bt_fullscreen = findViewById<ImageView>(R.id.bt_fullscreen)
        var intent = intent //getting the intent
        val postVideoPath = intent.getStringExtra("VIDEOLINK")//retrieving videolink passed in intent


        /**
         * Button to handle fullscreen functionality
         */
        bt_fullscreen.setOnClickListener(){
            if(!isFullScreen){
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fullscreen_exit))//changing icon for fullscreen exit
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE//setting orientation
            }else{
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fullscreen)) //changing icon for fullscreen
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //setting orientation

            }
            isFullScreen =!isFullScreen//updating boolean flag

        }



        
        simpleExoPlayer= SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)//setting skip forward timer
            .setSeekForwardIncrementMs(5000)//setting rewind timer
            .build()
        playerView.player = simpleExoPlayer
        playerView.keepScreenOn=true

        /**
         * Listener to handle media player operations
         */
        simpleExoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState==Player.STATE_BUFFERING){
                    progressBar.visibility= View.VISIBLE//enabling progress bar to communicate video duration

                }
                else if(playbackState==Player.STATE_READY){
                    progressBar.visibility=View.GONE//disabling progress bar
                }
            }

        })

        val videoSource = Uri.parse(postVideoPath)//setting media file path for palyer
        val mediaItem = MediaItem.fromUri(videoSource)
        //preparing and playing media
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()


    }

    /**
     * stopping media play progress
     */
    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()//stopping player
    }

    /**
     * Releases media play when video playback activity ends
     */
    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()//releasing player
    }

}