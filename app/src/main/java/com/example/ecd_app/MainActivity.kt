package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.retrofit.PostJS
import com.example.ecd_app.retrofit.PostsJS
import com.example.ecd_app.retrofit.RetrofitService
import com.example.ecd_app.retrofit.User
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
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
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PostListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //connection
        wordViewModel.allPosts.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }


        retrofitCall()
//        lifecycleScope.launch {
//            getSQL()
//        }
    }

    fun retrofitCall(){
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RetrofitService.ServiceBuilder.buildService().getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))

    }

    fun onResponse(response: List<User>){
        System.out.println(response)
    }

    fun onFailure(t: Throwable){
        System.out.println("Retrofit failed Failed: "+ t.stackTraceToString())
    }

    suspend fun getSQL() {
        // Use a different CoroutineScope, etc
        CoroutineScope(Dispatchers.IO).launch {
            var SQLConnector = SQLConnector()
            SQLConnector.getConnection()
            var queryResultSet = SQLConnector.executeMySQLQuery("SELECT * FROM wp_posts where ID=85;")
            System.out.println("Saving to db")
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            if (queryResultSet != null) {
                queryResultSet.next()
                System.out.println(queryResultSet.getString(1))
            }
            val post =
                queryResultSet?.let {
                    Post(
                        0,
                        "Title",
                        currentDate.toString(),
                        it.getString(1),
                        "meta"
                    )
                }
            System.out.println("Post created "+ post)
            if (post != null) {
                wordViewModel.insert(post)
                System.out.println("Checking db" + wordViewModel.allPosts.value)
            }
            SQLConnector.closeConnection()
        }
    }

}