package com.example.ecd_app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }
    var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text) as TextView
        textView!!.text = "Setting text"
        lifecycleScope.launch {
            //textView!!.text = "Setting text"
            //textView!!.text = getSQL()
            var s = getSQL()
        }
    }

    suspend fun getSQL(): String? {
        // Use a different CoroutineScope, etc
        var result: String? = null
        CoroutineScope(Dispatchers.IO).launch {
            var SQLConnector = SQLConnector()
            SQLConnector.getConnection()
            var queryResultSet = SQLConnector.executeMySQLQuery("SELECT * FROM wp_posts") //"SELECT post_content FROM wp_posts;"

            //saving to database
            System.out.println("Saving to db")
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            var row = 1
            while(queryResultSet!!.next()){
                System.out.println("Row : "+ row)
            queryResultSet!!.next()
                val columnsNumber: Int = queryResultSet!!.metaData.columnCount
                for(i in 1..columnsNumber){
                    System.out.println("Column :"+i + " "+queryResultSet.getString(i))
                }
                row++
//                val post =
//                    queryResultSet?.let {
//                        Post(
//                            0,
//                            "Title",
//                            currentDate.toString(),
//                            it.getString(1),
//                            "meta"
//                        )
//                    }
//                System.out.println("Post created "+ post)
//                result  = queryResultSet!!.getString(1)
//                System.out.println(result)
//                if (post != null) {
//                    wordViewModel.insert(post)
//                    System.out.println("Checking db" + wordViewModel.allPosts.value)
//                }
            }
            SQLConnector.closeConnection()
        }
        return result
    }

}