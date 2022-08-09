package com.example.ecd_app

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ECD Content List"

        setContentView(R.layout.activity_main)

        val categoryAll : AppCompatButton = findViewById(R.id.categoryAll)
        val categoryBtnBabyHealth : AppCompatButton = findViewById(R.id.categoryBabyHealth)
        val categoryBtnBabyDevelopment : AppCompatButton = findViewById(R.id.categoryBabyDevelopment)
        val categoryBtnParentHealth : AppCompatButton = findViewById(R.id.categoryParentHealth)
        val categoryBtnAssignedContent : AppCompatButton = findViewById(R.id.categoryAssignedContent)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        

        categoryAll.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            //need a method to change the list in the recycler view will be similar to the search methods
            allPostsDatabase()
        }

        categoryBtnBabyHealth.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            //need a method to change the list in the recycler view will be similar to the search methods
            getPostsByCategory("Baby Health")
        }

        categoryBtnBabyDevelopment.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            //need a method to change the list in the recycler view will be similar to the search methods
            getPostsByCategory("Baby Development")
        }

        categoryBtnParentHealth.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            //need a method to change the list in the recycler view will be similar to the search methods
            getPostsByCategory("Parent Health")
        }

        categoryBtnAssignedContent.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            //need a method to change the list in the recycler view will be similar to the search methods
            getPostsByCategory("Assigned Content")
        }


        adapter = PostListAdapter()
        val fetchPosts = findViewById<FloatingActionButton>(R.id.sync_content)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allPosts.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        fetchPosts.setOnClickListener(){
            Toast.makeText(this@MainActivity, "Fetching new posts :)", Toast.LENGTH_LONG).show()
           // wordViewModel.deleteAll()
            retrofitCall()
        }
        checkStoragePermission()
        checkReadStoragePermission()
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
        var posts: ArrayList<Post> = ArrayList<Post>()
        var videoLinks: ArrayList<String> = ArrayList<String>()
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
                assignedPost.category.get(0).name!!
            )
            if (post != null) {
                posts.add(post)
            }
            if (videoLink != null){
                videoLinks.add(videoLink)
            }
        }
        //launch background service
        val intent = Intent(this, OnRetrofitResponseAsyncTask::class.java)
        intent.putExtra("PostList",posts)
        intent.putExtra("VideoLinks",videoLinks)
        startService(intent)
    }

    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString())
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

    fun checkReadStoragePermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(this) //.setTitle(R.string.title_location_permission)
                    .setTitle("Title") //.setMessage(R.string.text_location_permission)
                    .setMessage("R.string.permission_request_message")
                    .setPositiveButton("R.string.permission_request_explaination",
                        DialogInterface.OnClickListener { dialogInterface, i -> //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                101
                            )
                        })
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    41
                )
            }
            false
        } else {
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val sync = menu?.findItem(R.id.menu_sync)


        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        val syncView = sync?.actionView as? androidx.appcompat.widget.AppCompatImageView

//        syncView?.text = "Sync"
        syncView?.setBackgroundDrawable(getDrawable(R.drawable.ic_postfetch ))

        searchView?.isSubmitButtonEnabled= true

        syncView?.setOnClickListener(){
            Toast.makeText(this@MainActivity,"buggy fetch", Toast.LENGTH_LONG).show()
            retrofitCall()

        }


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

    private fun getPostsByCategory(categoryQuery: String){
        wordViewModel.getPostByCategory(categoryQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }

        }

    }

    private fun allPostsDatabase(){
        wordViewModel.allPosts.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }


    }



}