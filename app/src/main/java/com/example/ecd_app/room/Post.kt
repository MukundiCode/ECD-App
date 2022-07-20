package com.example.ecd_app.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Post(@PrimaryKey (autoGenerate = true) val id: Int,
                val postTitle: String,
                val dateCreated: String,
                val postContent: String,
                val metaData: String) {


}