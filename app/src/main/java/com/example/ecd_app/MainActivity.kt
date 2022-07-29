package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.retrofit.*
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
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
//        wordViewModel.deleteAll()
//        retrofitCall()
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
        var videoLinks : List<String> = listOf("https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health_-The-Benefits-of-Breastfeeding.mp4",
            "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/yt5s.com-The-Road-to-Health-Introduction-Afrikaans.mp4",
            "https://ecdportal.azurewebsites.net/wp-content/uploads/2022/07/Services-Needed_vid.mp4")
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
            //for all links, download video
            for (link in videoLinks){

            }
        }
    }

    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString())
    }

}