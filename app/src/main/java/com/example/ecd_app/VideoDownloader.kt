package com.example.ecd_app

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import com.mysql.jdbc.Util.getPackageName


class VideoDownloader {

    fun downloadVideo(url: String?,vidoeName: String,context: Context ) {
        val Download_Uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(Download_Uri)

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false)
        // Visibility of the download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading video ")
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File "  + vidoeName )
        //Set the local destination for the downloaded file to a path within the application's external files directory
        /*request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_MOVIES, "Shivam196.mp4");*/ //For private destination
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(
            //Environment.DIRECTORY_MOVIES + "/ECD" ,
            Environment.DIRECTORY_MOVIES  ,
            //context.filesDir.absolutePath,
            vidoeName
        ) // for public destination
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        val downloadID = downloadManager!!.enqueue(request) // enqueue puts the download request in the queue.
    }
}