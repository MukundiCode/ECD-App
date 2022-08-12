package com.example.ecd_app

import android.app.Application
import com.example.ecd_app.room.PostsRepository
import com.example.ecd_app.room.PostsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ECDApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PostsRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PostsRepository(database.postDao()) }
    val videos  by lazy { fetchVideos(contentResolver)?.blockingGet() }
}