/**
 * @author Tinashe Mukundi Chitamba
 * This class defines the database model in the room database
 * The class is serializable
 */

package com.example.ecd_app.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey (autoGenerate = true) val id: Int,
    @SerialName("postTitle") val postTitle: String,
    @SerialName("dateCreated") val dateCreated: String,
    @SerialName("postContent") val postContent: String,
    @SerialName("videoName") val videoName: String?,
    @SerialName("metaData") val metaData: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(postTitle)
        parcel.writeString(dateCreated)
        parcel.writeString(postContent)
        parcel.writeString(videoName)
        parcel.writeString(metaData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}