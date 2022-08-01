package com.example.ecd_app

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.retrofit.*
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jsoup.Jsoup
import java.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PostListAdapter()
        val fetchPosts = findViewById<FloatingActionButton>(R.id.fab)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allPosts.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }

        fetchPosts.setOnClickListener(){
            Toast.makeText(this@MainActivity, "Fetching new posts :)", Toast.LENGTH_LONG).show()
            wordViewModel.deleteAll()
            retrofitCall()

        }
        wordViewModel.deleteAll()
        checkStoragePermission()
        retrofitCall()
    }

    fun retrofitCall(){
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RetrofitService.ServiceBuilder.buildService().getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
    }

    fun onResponse(response: User){
        //create list of video links
        for (assignedPost : AssignedPosts in response.assignedPosts){
            val post = Post(
                0,
                assignedPost.postTitle!!,
                assignedPost.postDate!!,
                assignedPost.postContent!!,
                "meta"
            )
            //add link to list
            System.out.println("Post created ")
            if (post != null) {
                wordViewModel.insert(post)
                System.out.println("Post inserted in database ")
            }
            //getLinkFromPost(assignedPost.postContent!!)
        }
    }

    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString())
    }

    fun downloadVideos(){
        var downloader = VideoDownloader()
        var videoLinks : List<String> = listOf("https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4",
            "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health-Introduction-Afrikaans.mp4",
            "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/Services-Needed_vid.mp4")
        //for all links, download video
        for (link in videoLinks){
            downloader.downloadVideo(link, this)
        }
    }

    /**
     * Returns true if this app has permission to access location, else, prompts the
     * user to accept or deny request for location permission.
     * @return boolean
     */
    fun checkStoragePermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(this) //.setTitle(R.string.title_location_permission)
                    .setTitle("Title") //.setMessage(R.string.text_location_permission)
                    .setMessage("R.string.permission_request_message")
                    .setPositiveButton("R.string.permission_request_explaination",
                        DialogInterface.OnClickListener { dialogInterface, i -> //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                101
                            )
                        })
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101
                )
            }
            false
        } else {
            true
        }
    }

    fun getLinkFromPost(xml : String) : String? {
        //clean up the wp postContent html jsoup
        var url : String? = null
        val doc: org.jsoup.nodes.Document? = Jsoup.parse(xml)
        val text: String? = doc?.text()
        val element = doc?.select("video")
        val srcUrl = element?.attr("src")
        if (srcUrl != null) {
            url = if (srcUrl.trim().isEmpty()){
                null
            }else{
                srcUrl
            }
            System.out.println("The url is "+ url)

        }
        return url
    }

}