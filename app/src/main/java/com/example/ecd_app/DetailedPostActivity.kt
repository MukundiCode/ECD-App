package com.example.ecd_app

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.*

class DetailedPostActivity : AppCompatActivity() {
//    private val defaulturl ="https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4"

    private var playbackPosition = 0
    private val defaulturl ="https://rr3---sn-4g5e6nze.googlevideo.com/videoplayback?expire=1658811994&ei=-iHfYtqZKJj5WpHpvbAN&ip=60.79.209.85&id=o-AIWA7OTapBKBENcW2dW0sGNz1avJPUW0RQlOhRL61tnm&itag=244&aitags=133%2C134%2C135%2C136%2C160%2C242%2C243%2C244%2C247%2C278%2C394%2C395%2C396%2C397%2C398&source=youtube&requiressl=yes&spc=lT-KhgE5-HNaI5EXXayZlORksr4BQZQ&vprv=1&mime=video%2Fwebm&ns=LTdDuB-CLhfjZt4bwUe0oJkH&gir=yes&clen=253088&dur=3.040&lmt=1637195016194282&keepalive=yes&fexp=24001373,24007246&c=WEB&rbqsm=fr&txp=1432434&n=dk6eKpGpof4BCA&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAJILhDyhFMlsCCQAiYSwdTq6tVkTQ9Y5o1-end_bK6oHAiB0dBt4KoXS6NRlpNpmeyQah1P79VYyA2bV9fL5ZCuNMg%3D%3D&rm=sn-ogueee7z,sn-wock7z&req_id=a60312e32bc1a3ee&ipbypass=yes&cm2rm=sn-jvhuxa15-j2ue7l&redirect_counter=3&cms_redirect=yes&cmsv=e&mh=gS&mip=2c0f:fba0:1:a0d0:c443:e54e:d868:13b8&mm=34&mn=sn-4g5e6nze&ms=ltu&mt=1658800125&mv=m&mvi=3&pl=48&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAI52XBhdJXPswkzP5lnfY105LX-hSyo1d9CRBo-PtQRNAiEAwF6op5Gt-7AW9r3xL-sfLaxG-oXWc4wW9Rq5x2mqWck%3D"

    private lateinit var pgBar : ProgressBar
    private lateinit var iPostVideoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var url : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detailed_post)

        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID",0)
        val postTitle = intent.getStringExtra("iPostTitle")
        val postDateCreated = intent.getStringExtra("iPostDate")
        val postContent = intent.getStringExtra("iPostContent")
        val postMetaData = intent.getStringExtra("iPostMetaData")

        //postcontent manipulate
//        val testString : String = "Coding can be difficult but don't give up"
//        val posOfBe : Int = testString.indexOf("be")
//        Toast.makeText(this@MainActivity, "the index of be in the test string is $posOfBe the expected position was 11" , Toast.LENGTH_LONG).show()
        Toast.makeText(this@DetailedPostActivity, postContent, Toast.LENGTH_LONG ).show()

        if (postContent != null) {
            url = if (postContent.contains("<video")){
                val initialPositionHttps : Int = postContent.indexOf("https:")
                var finalPosition : Int = postContent.indexOf(".mp4")
                finalPosition += 4
                postContent.substring(initialPositionHttps, finalPosition)


            }else{
                defaulturl
            }

//            d("urlPOST", url)

//            Toast.makeText(this@DetailedPostActivity, "$url", Toast.LENGTH_LONG).show()

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


//        var str= "<!-- wp:paragraph -->\n" +"<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vehicula in lectus non accumsan. Praesent at dui ante. Vestibulum eget leo vitae mi venenatis tristique ut non quam. Donec dictum risus purus, et porttitor ex vestibulum sit amet. Sed sed nibh eros. Donec blandit sodales libero id fringilla. Suspendisse aliquam efficitur volutpat. Praesent blandit metus id ipsum condimentum egestas. Integer quis sagittis eros. Duis posuere, purus suscipit molestie venenatis, nibh nunc aliquet dolor, id consequat nulla tortor vitae sem.</p>\n"
        if (str != null) {
            str  = str.replace("<[^>]*>".toRegex(), "")
        }
//        println(str)

//        val tvConsole : TextView = findViewById(R.id.tvConsole)
//        tvConsole.text= str


        tvPostTitle.text = postTitle
        if (str != null) {
            if (str.trim().isEmpty()){
                tvPostContent.text="This post does not have a text description"
                //need help hiding component
            }else{
                tvPostContent.text = str
            }
        }

    }


    override fun onStart() {
        super.onStart()

        val uri = Uri.parse(url)
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
}

