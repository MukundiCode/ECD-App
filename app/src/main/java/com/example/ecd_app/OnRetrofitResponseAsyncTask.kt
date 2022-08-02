package com.example.ecd_app

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsRepository
import com.example.ecd_app.room.PostsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class OnRetrofitResponseAsyncTask : Service()  {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    private var posts : ArrayList<Post> = ArrayList()
    private var videoLinks : ArrayList<String> = ArrayList()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var postsRepository : PostsRepository? = null


    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            try {
                //start work here
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        postsRepository = (this.application as ECDApplication).repository
    //        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
//            start()
//            // Get the HandlerThread's Looper and use it for our Handler
//            serviceLooper = looper
//            serviceHandler = ServiceHandler(looper)
//        }
    }

    fun downloadVideos(videoLinks: ArrayList<String>){
        var downloader = VideoDownloader()
        System.out.println("Downloading videos, number of videos: "+ videoLinks.size)

        for (link in videoLinks){
            if (link != null){
                val s = link?.split("/")
                var videoName = s.get(s.size-1)
                System.out.println("Downloading video with name: "+ videoName)
                downloader.downloadVideo(link,videoName, this)
            }
        }
    }
    fun getVideoLink(post_content : String): String? {
        var url: String? = null
        val doc: org.jsoup.nodes.Document? = Jsoup.parse(post_content)
        val element = doc?.select("video")
        val srcUrl = element?.attr("src")
        if (srcUrl != null) {
            url = if (srcUrl.trim().isEmpty()){
                null
            }else{
                srcUrl
            }
        }
        return url
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