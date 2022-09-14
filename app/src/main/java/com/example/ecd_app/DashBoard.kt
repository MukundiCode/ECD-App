package com.example.ecd_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView

/**
 * @author Suvanth Ramruthen
 *ECD Homepage containing all the activities that a user might explore
 */
class DashBoard : AppCompatActivity() {

    lateinit var preferences: SharedPreferences//shared preference file to read from


    /**
     * OnCreate function is called to create activity with relevant details and set onClickListeners for UI elements
     * @param savedInstanceState saved bundle state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)//setting layout file
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE) //getting details from shared preference
        val username = preferences.getString("NAME","")//getting logged in username

        //Creating views to reference XML components by IC
        val cardContent : CardView = findViewById(R.id.cardContent)
        val cardMapActivity : CardView = findViewById(R.id.cardMap)
        val cardArticlesActivity : CardView = findViewById(R.id.cardArticle)
        val cardSettings : CardView = findViewById(R.id.cardSettings)
        val cardInformation : CardView = findViewById(R.id.cardInfo)
        val cardLogout : CardView = findViewById(R.id.cardLogout)
        val tvGreetingUsername : TextView = findViewById(R.id.tvGreeting)
        tvGreetingUsername.text="${username}'s Dashboard" //Personalising dashboard


        /**
         * Launches ECD Content on click
         */
        cardContent.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        /**
         * Launches Facilities Search
         */
        cardMapActivity.setOnClickListener(){
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        /**
         * Launches public articles activity
         */
        cardArticlesActivity.setOnClickListener(){
            val intent = Intent(this, ArticleListActivity::class.java)
            startActivity(intent)
        }

        /**
         * Launches setting activity
         */
        cardSettings.setOnClickListener(){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }


        /**
         * Launches Bhabisana About Us
         */
        cardInformation.setOnClickListener(){
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        /**
         * Clears Shared Preference file and logs in the user
         */
        cardLogout.setOnClickListener(){
            val editor: SharedPreferences.Editor = preferences.edit() //shared preference file to edit
            editor.clear()//clearing shared preference file
            editor.apply()//applying clearance

            val intent = Intent(this, LoginActivity::class.java)//launching login ppage
            startActivity(intent)
            finish()
        }

    }

    /**
     * Creating a menu in the support action bar, contains code to launch help pdf manual in the pdf article renderer
     * @param menu argument for menu being created
     * @return true if created correctly
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.help_menu, menu)//inflating menu xml file
        val helpMenuItem = menu.findItem(R.id.menu_help)//ui component
        val helpView = helpMenuItem?.actionView as? androidx.appcompat.widget.AppCompatImageView
        helpView?.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_help_outline_24 ))//setting drawable
        helpView?.setOnClickListener(){
            val intent = Intent(this@DashBoard, ArticleActivity::class.java)//creating intent to pass article details
            val helpPDFObj = Article("Road To Help", "HelpDocument","placeholderHelp.pdf")
            intent.putExtra(EXTRA_ARTICLE_FNAME, helpPDFObj) //putting article file name extra for help
            startActivity(intent)
        }
        return true
    }
}