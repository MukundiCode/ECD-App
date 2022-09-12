package com.example.ecd_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ecd_app.retrofit.AuthResponse
import com.example.ecd_app.retrofit.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false
    var guestLoginBtn : Button ? = null

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
        guestLoginBtn = findViewById(R.id.btnGuest)
        val etUsername : EditText = findViewById(R.id.etUsername)

        loginBtn.setOnClickListener(){
            val name: String = etUsername.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME", name)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()
            retrofitCall(name.trim())
        }

        guestLoginBtn?.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val name = "Public"
            editor.putString("NAME", name)
            editor.putBoolean("CHECKBOX", true)
            editor.apply()
            Toast.makeText(this, "Logged in as guest", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun retrofitCall(username : String){
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RetrofitService.ServiceBuilder.buildService().authenticate(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
    }

    fun onResponse(response: AuthResponse){
        if(response.response == true){
            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this, "Login error, please try again", Toast.LENGTH_LONG).show()
        }
    }
    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString())
        guestLoginBtn?.setOnClickListener(){
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