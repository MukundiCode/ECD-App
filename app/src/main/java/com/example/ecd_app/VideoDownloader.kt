package com.example.ecd_app

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import com.downloader.*
import com.mysql.jdbc.Util.getPackageName


class VideoDownloader {

    fun downloadVideo(url: String?,context: Context ) {
        val Download_Uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(Download_Uri)

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false)
        // Visibility of the download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading")
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File")
        //Set the local destination for the downloaded file to a path within the application's external files directory
        /*request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_MOVIES, "Shivam196.mp4");*/ //For private destination
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(
            //Environment.DIRECTORY_MOVIES,
            "android.resource://" + context.packageName + "/",
            "testVideo.mp4"
        ) // for public destination
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        val downloadID = downloadManager!!.enqueue(request) // enqueue puts the download request in the queue.
    }



















//    fun downfile(url:String,fileName:String,dirPath:String,application: Application){
//        PRDownloader.initialize(application);
//        // Enabling database for resume support even after the application is killed:
//        // Enabling database for resume support even after the application is killed:
//        val config = PRDownloaderConfig.newBuilder()
//            .setDatabaseEnabled(true)
//            .build()
//        PRDownloader.initialize(application, config)
//
//        val downloadId = PRDownloader.download(url, dirPath, fileName)
//            .build()
//            .setOnStartOrResumeListener { }
//            .setOnPauseListener { }
//            .setOnCancelListener { }
//            .setOnProgressListener { }
//            .start(object : OnDownloadListener {
//                override fun onDownloadComplete() {
//                    System.out.println("Download finished")
//                }
//                override fun onError(error: com.downloader.Error?) {
//                    System.out.println(error.toString()
//                }
//
//            })
//
//
//    }


}