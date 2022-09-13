/**
 * @author Tinashe Mukundi Chitamba
 * This is the repository class for the room database.
 * Ensures single instance of database connection
 */

package com.example.ecd_app.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class PostsRepository(private val postDAO: PostDAO) {

    val allPosts: Flow<List<Post>> = postDAO.getAlphabetizedPosts()

    /**
     * @param post Post to insert to database
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        postDAO.insert(post)
    }

    /**
     * @param post_title Title as search query
     * @return Boolean value for when it exists
     */
    @WorkerThread
    fun exists(post_title: String): Boolean {
        return postDAO.exists(post_title)
    }

    fun existsAsync(post_title: String) {
        Executors.newSingleThreadExecutor().execute {
            val num  = exists(post_title)
        }
    }

    /**
     * Deletes all rows in database
     */
    @WorkerThread
    suspend fun deleteAll() {
        postDAO.deleteAll()
    }

    /**
     * @param searchQuery
     * @return Posts
     */
    fun searchDatabase(searchQuery: String): Flow<List<Post>> {
        return postDAO.searchDatabase(searchQuery)
    }

    /**
     * @param filterQuery
     * @return Posts
     */
    fun filterDatabase(filterQuery: String): Flow<List<Post>> {
        return postDAO.filterDatabase(filterQuery)
    }

    /**
     * @param meta
     * @return Posts
     */
    fun getByCategory(meta: String): Flow<List<Post>> {
        return postDAO.getPostsByCategory(meta)
    }

    /**
     * @param title
     * @return Posts
     */
    fun getByTitle(title: String): List<Post> {
        return postDAO.getPostsByTitle(title)
    }

}