package com.example.ecd_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView

class DashBoard : AppCompatActivity() {

    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = preferences.getString("NAME","")

        val cardContent : CardView = findViewById(R.id.cardContent)
        val cardMapActivity : CardView = findViewById(R.id.cardMap)
        val cardArticlesActivity : CardView = findViewById(R.id.cardArticle)
        val cardSettings : CardView = findViewById(R.id.cardSettings)
        val cardInformation : CardView = findViewById(R.id.cardInfo)
        val cardLogout : CardView = findViewById(R.id.cardLogout)

        val tvGreetingUsername : TextView = findViewById(R.id.tvGreeting)
        tvGreetingUsername.text="${username}'s Dashboard"


        cardContent.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cardMapActivity.setOnClickListener(){
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        cardArticlesActivity.setOnClickListener(){
            val intent = Intent(this, ArticleListActivity::class.java)
            startActivity(intent)
        }

        cardSettings.setOnClickListener(){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        cardInformation.setOnClickListener(){
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        cardLogout.setOnClickListener(){
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}