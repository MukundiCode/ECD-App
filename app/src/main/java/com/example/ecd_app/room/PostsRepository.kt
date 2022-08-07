package com.example.ecd_app.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class PostsRepository(private val postDAO: PostDAO) {

    val allPosts: Flow<List<Post>> = postDAO.getAlphabetizedPosts()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        postDAO.insert(post)
    }

    @WorkerThread
    fun exists(post_title: String): Boolean {
        return postDAO.exists(post_title)
    }

    fun existsAsync(post_title: String) {
        Executors.newSingleThreadExecutor().execute {
            val num  = exists(post_title)
            //liveWords.postValue(words)
        }
    }

    @WorkerThread
    suspend fun deleteAll() {
        postDAO.deleteAll()
    }

    //suvanth
    fun searchDatabase(searchQuery: String): Flow<List<Post>> {
        return postDAO.searchDatabase(searchQuery)
    }

    fun filterDatabase(filterQuery: String): Flow<List<Post>> {
        return postDAO.filterDatabase(filterQuery)
    }
}