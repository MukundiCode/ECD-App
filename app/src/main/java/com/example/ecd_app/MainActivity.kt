package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import androidx.lifecycle.lifecycleScope


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            getSQL()
            //getRSS()
        }
    }

    suspend fun getSQL() {
        // Use a different CoroutineScope, etc
        CoroutineScope(Dispatchers.IO).launch {
            var SQLConnector = SQLConnector()
            SQLConnector.getConnection()
            SQLConnector.executeMySQLQuery()
        }
    }

//    suspend fun getRSS() {
//        // Use a different CoroutineScope, etc
//        CoroutineScope(Dispatchers.IO).launch {
//            var rss = RSSUtil()
//            rss.test()
//        }
//    }
}