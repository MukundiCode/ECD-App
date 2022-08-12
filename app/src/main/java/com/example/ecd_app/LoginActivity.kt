package com.example.ecd_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if (isRemembered){
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }

        val loginBtn : Button = findViewById(R.id.btnLogin)
        val guestLoginBtn : Button = findViewById(R.id.btnGuest)

        val etUsername : EditText = findViewById(R.id.etUsername)

        loginBtn.setOnClickListener(){
            val name: String = etUsername.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME", name)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()

            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()

        }

        guestLoginBtn.setOnClickListener(){
            val name: String = "Guests"
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME", name)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()

            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }











    }
}