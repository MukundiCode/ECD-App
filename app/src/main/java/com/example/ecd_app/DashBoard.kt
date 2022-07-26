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
        cardContent.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val webview : CardView = findViewById(R.id.cardSearch)
        webview.setOnClickListener(){
            val intent = Intent(this, postContentWebView::class.java)
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