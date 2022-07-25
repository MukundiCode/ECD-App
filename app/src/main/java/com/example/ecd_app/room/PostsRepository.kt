package com.example.ecd_app.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PostsRepository(private val postDAO: PostDAO) {

    val allPosts: Flow<List<Post>> = postDAO.getAlphabetizedPosts()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        postDAO.insert(post)
    }
}