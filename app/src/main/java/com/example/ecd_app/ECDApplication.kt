/**
 * @author Tinashe Mukundi Chitamba
 * This is the singleton for the application.
 *
 */

package com.example.ecd_app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.ecd_app.room.PostsRepository
import com.example.ecd_app.room.PostsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * database : Database singleton
 * repository : room repository singleton
 * videos : List<LocalVideoData>
 */
class ECDApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PostsRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PostsRepository(database.postDao()) }
    val videos  by lazy { fetchVideos(contentResolver)?.blockingGet() }

    /**
     * Gets the user username from shared preferences
     */
    fun getUsernameFromPreferences(): String {
        var preferences: SharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = preferences.getString("NAME","")
        if (username != null){
            return username
        }else{
            return "Public"
        }
    }
}