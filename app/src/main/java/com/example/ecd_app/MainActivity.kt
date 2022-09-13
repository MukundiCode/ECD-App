/**
 * @author Tinashe Mukundi Chitamba and Suvanth Ramruthen
 */

package com.example.ecd_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.retrofit.*
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var adapter : PostListAdapter
    private lateinit var postStatusCommunicationTV : TextView
    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }

    /**
     * Initialize buttons
     * Initialize adapter
     * Check permissions
     * @param savedInstanceState
     */
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
        postStatusCommunicationTV = findViewById(R.id.textViewPostStatus)


        categoryAll.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            allPostsDatabase()
        }

        categoryBtnBabyHealth.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            getPostsByCategory("Baby Health")
        }

        categoryBtnBabyDevelopment.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            getPostsByCategory("Baby Development")
        }

        categoryBtnParentHealth.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            getPostsByCategory("Parent Health")
        }

        categoryBtnAssignedContent.setOnClickListener(){
            categoryAll.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnBabyDevelopment.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnParentHealth.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_initial))
            categoryBtnAssignedContent.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_clicked))
            getPostsByCategory("Assigned Content")
        }

        adapter = PostListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel.allPosts.observe(this) { words ->
            val rootView = window.decorView.rootView
            Snackbar.make(rootView,"Showing ${words.size} posts ",Snackbar.LENGTH_SHORT).setAnchorView(R.id.textViewanchor).show()
            words.let { adapter.submitList(it) }
        }
        Permissions().checkStoragePermission(this)
        Permissions().checkReadStoragePermission(this)
    }

    /**
     * Checks if connected to wifi, then makes retrofit call. Creates composite disposable
     * then observes until response
     */
    fun retrofitCall(){
        val connection: String = checkWifi(this)
        if(connection == "WIFI"){
            val compositeDisposable = CompositeDisposable()
            val username = (this.application as ECDApplication).getUsernameFromPreferences()
            compositeDisposable.add(
                RetrofitService.ServiceBuilder.buildService().getPosts(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))

        } else if (connection == "CELLULAR"){
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            val compositeDisposable = CompositeDisposable()
                            val username = (this.application as ECDApplication).getUsernameFromPreferences()
                            compositeDisposable.add(
                                RetrofitService.ServiceBuilder.buildService().getPosts(username)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {}
                    }
                }

            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to use mobile data to sync ECD content? This might deplete your data.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

        } else if (connection == "OFFLINE"){
            Toast.makeText(this, "Can't sync when offline, please connect to the internet", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Checks if phone connected to wifi or cellular
     * @param context
     * @return String
     */
    @SuppressLint("NewApi")
    fun checkWifi(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return "CELLULAR"
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return "WIFI"
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return "ETHERNET"
                }
            }
        }
        return "OFFLINE"
    }

    /**
     * Function called on retrofit response
     * Gets list of posts, and starts the background service
     * for adding posts to database and downloading videos
     * @param response
     */
    fun onResponse(response: List<PostJS>){
        var posts: ArrayList<Post> = ArrayList<Post>()
        var videoLinks: ArrayList<String> = ArrayList<String>()
        var videoName: String? = "None"
        for (assignedPost : PostJS in response){
            var videoLink: String? = if (getVideoLink(assignedPost.post?.postContent!!) != null){
                getVideoLink(assignedPost.post!!.postContent!!)
            }else{
                null
            }
            if (videoLink != null){
                val s = videoLink?.split("/")
                videoName = s?.get(s.size-1)
            }
            System.out.println(assignedPost.category)
            val post = Post(
                0,
                assignedPost.post!!.postTitle!!,
                assignedPost.post!!.postDate!!,
                assignedPost.post!!.postContent!!,
                videoName!!,
                assignedPost.category!!
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

    /**
     * On retrofit call fail
     * @param t : throwable
     */
    fun onFailure(t: Throwable){
        //System.out.println("Retrofit Failed: "+ t.stackTraceToString())
        Toast.makeText(this@MainActivity,"Syncing failed", Toast.LENGTH_LONG).show()
    }

    /**
     * Extracts the video link from post content
     *
     * @param post_content
     * @return link
     */
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val sync = menu?.findItem(R.id.menu_sync)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        val syncView = sync?.actionView as? androidx.appcompat.widget.AppCompatImageView
        syncView?.setBackgroundDrawable(getDrawable(R.drawable.ic_postfetch ))
        searchView?.isSubmitButtonEnabled= true
        syncView?.setOnClickListener(){
            if (Permissions().checkStoragePermission(this) && Permissions().checkReadStoragePermission(this)){
                Toast.makeText(this@MainActivity,"Fetching", Toast.LENGTH_LONG).show()
                retrofitCall()
            }
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
                if (it.isEmpty()){
                    postStatusCommunicationTV.visibility = View.VISIBLE
                }else{
                    postStatusCommunicationTV.visibility = View.INVISIBLE
                }
                adapter.submitList(it)

            }
        }
    }

    private fun getPostsByCategory(categoryQuery: String){
        val rootView = window.decorView.rootView
        wordViewModel.getPostByCategory(categoryQuery).observe(this) { list ->
            list.let {
                if (it.isEmpty()){
                    postStatusCommunicationTV.visibility = View.VISIBLE
                }else{
                    postStatusCommunicationTV.visibility = View.INVISIBLE
                }
                if (it.size==1){
                    Snackbar.make(rootView,"Showing ${it.size} post in $categoryQuery",Snackbar.LENGTH_SHORT).setAnchorView(R.id.textViewanchor).show()
                }
                if(it.size>1) {
                    Snackbar.make(rootView,"Showing ${it.size} posts in $categoryQuery",Snackbar.LENGTH_SHORT).setAnchorView(R.id.textViewanchor).show()
                }
                adapter.submitList(it)
            }
        }
    }

    private fun allPostsDatabase(){
        wordViewModel.allPosts.observe(this) { words ->
            if (words.isEmpty()){
                postStatusCommunicationTV.visibility = View.VISIBLE
            }else{
                postStatusCommunicationTV.visibility = View.INVISIBLE
                val rootView = window.decorView.rootView
                Snackbar.make(rootView,"Showing ${words.size} posts ",Snackbar.LENGTH_SHORT).setAnchorView(R.id.textViewanchor).show()
            }
            words.let { adapter.submitList(it) }
        }
    }
}