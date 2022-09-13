/**
 * @author Tinashe Mukundi Chitamba
 * This is the room database object
 * It initializes the sqlite database
 */

package com.example.ecd_app.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = arrayOf(Post::class),
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 2, to = 3)
]
)
public abstract class PostsRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDAO

    private class PostDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        /**
         * TODO : Remove initial post
         *
         * @param db SQLite Database
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //nothing yet
                    var postDao = database.postDao()
                    var post = Post(0,
                        "BreastFeeding at work",
                        "July",
                        "date",
                        "None",
                        "meta")
                    postDao.insert(post)
                }
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PostsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsRoomDatabase::class.java,
                    "posts_database"
                ).addCallback(PostDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}