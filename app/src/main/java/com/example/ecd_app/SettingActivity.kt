package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import com.example.ecd_app.room.PostsViewModel
import com.example.ecd_app.room.PostsViewModelFactory

class SettingActivity : AppCompatActivity() {

    private val wordViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as ECDApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"



        val cardSync : CardView = findViewById(R.id.cardSync)
        val cardDelete : CardView = findViewById(R.id.cardDelete)
        val cardPermissions : CardView = findViewById(R.id.cardPermission)
        val cardVersion : CardView = findViewById(R.id.cardVersion)

        cardSync.setOnClickListener(){
            Toast.makeText(this@SettingActivity, "Content Synced", Toast.LENGTH_SHORT).show()

        }

        cardDelete.setOnClickListener(){
            wordViewModel.deleteAll()
            Toast.makeText(this@SettingActivity, "Content Cleared", Toast.LENGTH_SHORT).show()


        }

        cardPermissions.setOnClickListener(){
            Toast.makeText(this@SettingActivity, "Opened app permissions", Toast.LENGTH_SHORT).show()


        }

        cardVersion.setOnClickListener(){
            Toast.makeText(this@SettingActivity, "Current Version 1", Toast.LENGTH_SHORT).show()
        }


    }
}