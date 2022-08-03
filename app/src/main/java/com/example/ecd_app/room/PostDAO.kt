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

    @Query("SELECT * FROM posts_table ORDER BY postTitle ASC")
    fun getAlphabetizedPosts(): Flow<List<Post>>

    //suvanth
    @Query("SELECT * FROM posts_table WHERE postTitle LIKE :searchQuery OR postContent LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Post>>

    @Query("DELETE FROM posts_table")
    suspend fun deleteAll()
}