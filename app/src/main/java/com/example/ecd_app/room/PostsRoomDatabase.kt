package com.example.ecd_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Post::class), version = 1, exportSchema = false)
public abstract class PostsRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDAO

    private class PostDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //nothing yet
                    var postDao = database.postDao()
                    var post = Post(0,"Title","Content","date","meta")
                    postDao.insert(post)
                    System.out.println("Database created")
                }
            }
        }

    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PostsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsRoomDatabase::class.java,
                    "posts_database"
                ).addCallback(PostDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}