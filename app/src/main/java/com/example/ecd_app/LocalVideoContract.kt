package com.example.ecd_app

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_ALBUM
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_DATE_ADDED
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_DURATION
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_NAME
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_PATH
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_RESOLUTION
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_SIZE
import com.example.ecd_app.LocalVideoContract.Companion.VIDEO_TYPE
import com.example.ecd_app.LocalVideoContract.Companion.projection
import com.example.ecd_app.LocalVideoContract.Companion.queryOrder
import com.example.ecd_app.LocalVideoContract.Companion.queryUri
import com.example.ecd_app.LocalVideoContract.Companion.selection
import com.example.ecd_app.LocalVideoContract.Companion.selectionArgs
import io.reactivex.Single

class LocalVideoContract {
    companion object {
        const val VIDEO_NAME = MediaStore.Video.Media.DISPLAY_NAME
        const val VIDEO_PATH = MediaStore.Video.Media.DATA
        const val VIDEO_TYPE = MediaStore.Video.Media.MIME_TYPE
        const val VIDEO_DURATION = MediaStore.Video.Media.DURATION
        const val VIDEO_SIZE = MediaStore.Video.Media.SIZE
        const val VIDEO_DATE_ADDED = MediaStore.Video.Media.DATE_ADDED
        const val VIDEO_ALBUM = MediaStore.Video.Media.ALBUM
        const val VIDEO_RESOLUTION = MediaStore.Video.Media.RESOLUTION

        // The Uri path
        fun queryUri(): Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        //fun projection() = arrayOf(VIDEO_NAME, VIDEO_PATH, VIDEO_TYPE, VIDEO_DURATION, VIDEO_SIZE, VIDEO_DATE_ADDED, VIDEO_ALBUM, VIDEO_RESOLUTION)
        fun projection() = arrayOf(VIDEO_NAME,VIDEO_PATH, VIDEO_TYPE, VIDEO_DURATION, VIDEO_SIZE, VIDEO_DATE_ADDED)


        fun selection(): String {
            val argsSize = selectionArgs().size
            val str = StringBuilder()
            for (i in 0 until argsSize) {
                if (i > 0) {
                    str.append(",")
                }
                str.append("?")
            }
            return VIDEO_TYPE.plus(" in (").plus(str.toString()).plus(")")
        }

        fun selectionArgs() = arrayOf("video/mp4", "video/m4v", "video/mkv", "video/mov", "video/avi", "video/ts", "video/webm")
        fun queryOrder() = "$VIDEO_DATE_ADDED DESC"
    }
}
@SuppressLint("Range")
fun fetchVideos(contentResolver: ContentResolver): Single<List<LocalVideoData>> {
    return Single.create<List<LocalVideoData>> {
        try {
            val cursor = contentResolver.query(queryUri(), projection(), selection(), selectionArgs(), queryOrder())
            val videosList = mutableListOf<LocalVideoData>()
            var idx = 0
            System.out.println("cursor is "+cursor)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        System.out.println(cursor.getColumnIndex(VIDEO_NAME))
                        videosList.add(LocalVideoData(
                            cursor.getString(cursor.getColumnIndex(VIDEO_NAME)),
                            cursor.getString(cursor.getColumnIndex(VIDEO_PATH)),
                            cursor.getString(cursor.getColumnIndex(VIDEO_TYPE)),
                            cursor.getLong(cursor.getColumnIndex(VIDEO_DURATION)),
                            cursor.getLong(cursor.getColumnIndex(VIDEO_SIZE)),
                            cursor.getLong(cursor.getColumnIndex(VIDEO_DATE_ADDED))
                            //cursor.getString(cursor.getColumnIndex(VIDEO_RESOLUTION))
                        )
                        )
                        idx++
                    } while (cursor.moveToNext())
                }
            }
            cursor?.close()
            it.onSuccess(videosList)
        } catch (ex: Exception) {
            it.onError(ex)
        }
    }
}
