package com.example.ecd_app

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

class OnRetrofitResponseAsyncTask : Service()  {

    private var serviceHandler: ServiceHandler? = null
    private var posts : ArrayList<Post> = ArrayList()
    private var videoLinks : ArrayList<String> = ArrayList()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var postsRepository : PostsRepository? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        postsRepository = (this.application as ECDApplication).repository
    }

    fun downloadVideos(videoLinks: ArrayList<String>){
        var downloader = VideoDownloader()
        System.out.println("Downloading videos, number of videos: "+ videoLinks.size)
        var videos = (this.application as ECDApplication).videos
        var videoNames = ArrayList<String>()
        if (videos != null) {
            for (v in videos) {
                v.VIDEO_NAME?.let { videoNames.add(it) }
            }
        }

        for (link in videoLinks){
            if (link != null){
                val s = link?.split("/")
                var videoName = s?.get(s.size-1)
                if (!videoNames.contains(videoName)){
                    if (videoName != null && videoName != "None") {
                        downloader.downloadVideo(link,videoName, this)
                        System.out.println("Downloading video with name: "+ videoName)
                    }
                }else{
                    System.out.println("Video already exists")
                }
            }
        }
    }

    suspend fun addToDatabase(posts : ArrayList<Post>){
        for (post in posts){
            if (post != null) {
                if (!postsRepository!!.exists(post.postTitle)){
                    postsRepository!!.insert(post)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
        posts = intent.getParcelableArrayListExtra<Post>("PostList") as ArrayList<Post>
        videoLinks = intent.getParcelableArrayListExtra<Parcelable>("VideoLinks") as ArrayList<String>

        scope.launch {
            addToDatabase(posts)
        }
        downloadVideos(videoLinks)
        //downlaod videos
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
}