package com.example.ecd_app

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class ContactActivity : AppCompatActivity() {
    var imageArray: ArrayList<Int> = ArrayList()
    lateinit var carouselView: CarouselView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Information"
        val facebookImage : ImageView = findViewById(R.id.imageFacebook)
        val instagramImage : ImageView = findViewById(R.id.imageInstagram)
        val whatsappImage : ImageView = findViewById(R.id.imageWhatsapp)
        val ecdImage : ImageView = findViewById(R.id.imageBbpPortal)

        fun isAppInstalled(packageName: String): Boolean {
            val pm: PackageManager = getPackageManager()
            return try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        facebookImage.setOnClickListener(){
            val url: String = "https://www.facebook.com/bhabhisanababyproject/"
            val fb = Intent(Intent.ACTION_VIEW)
            fb.data = Uri.parse(url)
            startActivity(fb)
        }

        instagramImage.setOnClickListener(){
            val url: String = "https://www.instagram.com/bhabhisanababyproject/?igshid=YmMyMTA2M2Y="
            val ig = Intent(Intent.ACTION_VIEW)
            ig.data = Uri.parse(url)
            startActivity(ig)

        }

        whatsappImage.setOnClickListener(){
            val result = isAppInstalled("com.whatsapp")
            if(result){
                val url: String = "https://chat.whatsapp.com/HrqX8TOScucI2LDIUpEXvI"
                val whatsapp = Intent(Intent.ACTION_VIEW)
                whatsapp.data = Uri.parse(url)
                startActivity(whatsapp)
            }else{
                Toast.makeText(this@ContactActivity, "Install WhatsApp first", Toast.LENGTH_LONG).show()
            }



        }

        ecdImage.setOnClickListener(){
            val url: String = "https://ecdportal.azurewebsites.net/"
            val ecd = Intent(Intent.ACTION_VIEW)
            ecd.data = Uri.parse(url)
            startActivity(ecd)

        }
        imageArray.add(R.drawable.one)
        imageArray.add(R.drawable.two)
        imageArray.add(R.drawable.three)
        imageArray.add(R.drawable.four)

        carouselView=findViewById(R.id.carouselView)
        carouselView.pageCount=imageArray.size

        carouselView.setImageListener(imageListener)

    }
    var imageListener = ImageListener { position, imageView -> imageView.setImageResource(imageArray[position]) }
}