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

/**
 * @author Suvanth Ramruthen
 * Login activity presents users with two options i.e. to enter the username assigned by BBP via the Wordpress web portal or to continue as a guest
 */
class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences //sp file to manage login status
    var isRemembered = false//remember login status
    var guestLoginBtn : Button ? = null//guest login

    /**
     * Function called to setup the login activity with relevant details
     * @param savedInstanceState saved bundle state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)//setting login view as the resource file
        supportActionBar?.hide()//hiding actionbar for login screen


        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)//getting sp file
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)//checking login status

        /**
         * if logged in this code is executed launching the dashboard
         */
        if (isRemembered){
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }

        //views for ui components
        val loginBtn : Button = findViewById(R.id.btnLogin)
        guestLoginBtn = findViewById(R.id.btnGuest)
        val etUsername : EditText = findViewById(R.id.etUsername)


        loginBtn.setOnClickListener(){
            val name: String = etUsername.text.toString()//setting name equal to username dit text
            val editor: SharedPreferences.Editor = sharedPreferences.edit()//editing shared prefernce
            editor.putString("NAME", name)//putting in username
            editor.putBoolean("CHECKBOX", true)//remembering login
            editor.apply()//saving changes to sp
            retrofitCall(name.trim())//call to retrofit to authenticate user
        }

        guestLoginBtn?.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()//editing shared preference
            val name = "Public"//setting name for guest account
            editor.putString("NAME", name)//saving username to sp
            editor.putBoolean("CHECKBOX", true)//remember guest login
            editor.apply()//saving changes
            Toast.makeText(this, "Logged in as guest", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DashBoard::class.java)//starting dashboard activity
            startActivity(intent)
            finish()
        }
    }

    /**
     * User authentication
     * @param username username of account to be verified for authenticity
     */
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
            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show()//showing login status has been saved
            val intent = Intent(this, DashBoard::class.java)//launching  dashboard activity after succesful login
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this, "Login error, please try again", Toast.LENGTH_LONG).show()//indicating login did not succeed
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