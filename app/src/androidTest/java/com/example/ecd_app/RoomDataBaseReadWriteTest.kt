package com.example.ecd_app

import android.content.Context
import androidx.activity.viewModels
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecd_app.room.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.hamcrest.Matchers.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDataBaseReadWriteTest {
    private lateinit var postDao: PostDAO
    private lateinit var db: PostsRoomDatabase
    private lateinit var  postsRepository: PostsRepository
    val scope = GlobalScope

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        postsRepository = (context as ECDApplication).repository
        db = Room.inMemoryDatabaseBuilder(
            context, PostsRoomDatabase::class.java).build()
        postDao = db.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWrite() {
        val post = Post(
            0,
            "Test Title",
            "Test Date",
            "Test content",
            "Test Video Name",
            "Test Category"
        )
        scope.launch {
            postsRepository.insert(post)
        }
        val byTitle = postsRepository.getByTitle("Test Title")
        assertEquals(byTitle.get(0).postTitle, post.postTitle)
    }
//    @Test
//    @Throws(Exception::class)
//    fun testDeleteByTitle(){
//        val post = Post(
//            0,
//            "Test Title",
//            "Test Date",
//            "Test content",
//            "Test Video Name",
//            "Test Category"
//        )
//        scope.launch {
//            postsRepository.insert(post)
//        }
//        val byTitle = postsRepository.getByTitle("Test Title")
//        assertEquals(byTitle.get(0).postTitle, post.postTitle)
//        scope.launch {
//            postsRepository.deleteByTitle("Test Title")
//        }
//        assertEquals(byTitle.size, 0)
//    }

    @Test
    @Throws(Exception::class)
    fun testExists(){
        val post = Post(
            0,
            "Test Title",
            "Test Date",
            "Test content",
            "Test Video Name",
            "Test Category"
        )
        scope.launch {
            postsRepository.insert(post)
        }
        val exists = postsRepository.exists("Test Title")
        assertEquals(true,exists)
    }

    @Test
    @Throws(Exception::class)
    fun testGetByTitle(){
        val post = Post(
            0,
            "Test Title",
            "Test Date",
            "Test content",
            "Test Video Name",
            "Test Category"
        )
        scope.launch {
            postsRepository.insert(post)
        }
        val byTitle = postsRepository.getByTitle("Test Title")
        assertEquals(byTitle.get(0).postTitle, post.postTitle)
    }



}