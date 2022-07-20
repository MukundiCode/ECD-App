package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import androidx.lifecycle.lifecycleScope
import com.example.ecd_app.room.Post
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            getSQL()
        }
    }

    suspend fun getSQL() {
        // Use a different CoroutineScope, etc
        CoroutineScope(Dispatchers.IO).launch {
            var SQLConnector = SQLConnector()
            SQLConnector.getConnection()
            var queryResultSet = SQLConnector.executeMySQLQuery("SELECT post_content FROM wp_posts where ID=85;")

            //saving to database
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