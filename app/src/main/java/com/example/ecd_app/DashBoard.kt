package com.example.ecd_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.cardview.widget.CardView

class DashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val cardContent : CardView = findViewById(R.id.cardContent)
        val cardMapActivity : CardView = findViewById(R.id.cardMap)

        cardContent.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cardMapActivity.setOnClickListener(){
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

//        val btnContent : Button = findViewById(R.id.btnContent)
//
//        btnContent.setOnClickListener(){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}