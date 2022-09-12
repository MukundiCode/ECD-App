package com.example.ecd_app

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions {

    fun checkStoragePermission(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(context) //.setTitle(R.string.title_location_permission)
                    .setTitle("Allow write storage permission") //.setMessage(R.string.text_location_permission)
                    .setMessage(R.string.permission_request_message)
                    .setPositiveButton(R.string.permission_request_explaination,
                        DialogInterface.OnClickListener { dialogInterface, i -> //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                context,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                101
                            )
                        })
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    context , arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101
                )
            }
            false
        } else {
            true
        }
    }

    fun checkReadStoragePermission(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(context) //.setTitle(R.string.title_location_permission)
                    .setTitle("Allow Read storage permission") //.setMessage(R.string.text_location_permission)
                    .setMessage(R.string.permission_request_message)
                    .setPositiveButton(R.string.permission_request_explaination,
                        DialogInterface.OnClickListener { dialogInterface, i -> //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                context,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                101
                            )
                        })
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    41
                )
            }
            false
        } else {
            true
        }
    }

}