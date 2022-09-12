/**
 * @author Tinashe Mukundi Chitamba and Suvanth Ramruthen
 * This interface defines the DAO for making SQL queries in the room database
 */

package com.example.ecd_app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: Post)

    /**
     * Get all posts in alphabetical order
     */
    @Query("SELECT * FROM posts_table ORDER BY postTitle ASC")
    fun getAlphabetizedPosts(): Flow<List<Post>>

    /**
     * Get all posts by category/meta
     */
    @Query("SELECT * FROM posts_table WHERE metaData = :metaData")
    fun getPostsByCategory(metaData: String): Flow<List<Post>>

    /**
     * Get one post by title
     */
    @Query("SELECT * FROM posts_table WHERE postTitle = :post_title")
    fun getPostsByTitle(post_title: String): List<Post>

    /**
     * Search database given a query
     */
    @Query("SELECT * FROM posts_table WHERE postTitle LIKE :searchQuery OR postContent LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Post>>

    /**
     * Filter results by query in title
     */
    @Query("SELECT * FROM posts_table WHERE postTitle = :filterQuery")
    fun filterDatabase(filterQuery: String): Flow<List<Post>>

    /**
     * Check if entry exists in database
     */
    @Query("SELECT EXISTS (SELECT * FROM posts_table WHERE postTitle = :post_title)")
    fun exists(post_title: String): Boolean

    /**
     * Delete all entries from the database
     */
    @Query("DELETE FROM posts_table")
    suspend fun deleteAll()
}