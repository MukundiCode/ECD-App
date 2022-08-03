package com.example.ecd_app

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var adapter : PostListAdapter
    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }
    val postVideoLinks : ArrayList<String?> = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = PostListAdapter()
        val fetchPosts = findViewById<FloatingActionButton>(R.id.sync_content)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allPosts.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        fetchPosts.setOnClickListener(){
            Toast.makeText(this@MainActivity, "Fetching new posts :)", Toast.LENGTH_LONG).show()
            wordViewModel.deleteAll()
            retrofitCall()
        }
        checkStoragePermission()
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
        for (assignedPost : AssignedPosts in response.assignedPosts){
            var videoLink: String? = if (getVideoLink(assignedPost.postContent!!) != null){
                                            getVideoLink(assignedPost.postContent!!)
                                        }else{
                                             null
                                        }
            val s = videoLink?.split("/")
            var videoName = s?.get(s.size-1)
            val post = Post(
                0,
                assignedPost.postTitle!!,
                assignedPost.postDate!!,
                assignedPost.postContent!!,
                videoName!!,
                "meta"
            )
            System.out.println("Post created ")
            if (post != null) {
                wordViewModel.insert(post)
                System.out.println("Post inserted in database ")
            }
            if (videoLink != null){
                System.out.println("URL is :"+ videoLink)
                postVideoLinks.add(videoLink)
            }
        }
        downloadVideos(postVideoLinks)
    }

    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString())
    }

    fun downloadVideos(videoLinks : ArrayList<String?>){
        var downloader = VideoDownloader()
        System.out.println("Downloading videos, number of videos: "+ videoLinks.size)

        for (link in videoLinks){
            if (link != null){
                val s = link?.split("/")
                var videoName = s.get(s.size-1)
                System.out.println("Downloading video with name: "+ videoName)
                downloader.downloadVideo(link,videoName, this)
                System.out.println("DONE test")
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled= true
        searchView?.setOnQueryTextListener(this@MainActivity)
        return true



    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"
        wordViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }

        }

    }
}