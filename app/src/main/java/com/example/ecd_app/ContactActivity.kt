package com.example.ecd_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class ContactActivity : AppCompatActivity() {
    var imageArray: ArrayList<Int> = ArrayList()
    lateinit var carouselView: CarouselView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val facebookImage : ImageView = findViewById(R.id.imageFacebook)
        val instagramImage : ImageView = findViewById(R.id.imageInstagram)
        val whatsappImage : ImageView = findViewById(R.id.imageWhatsapp)
        val ecdImage : ImageView = findViewById(R.id.imageBbpPortal)

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
            val url: String = "https://chat.whatsapp.com/HrqX8TOScucI2LDIUpEXvI"
            val whatsapp = Intent(Intent.ACTION_VIEW)
            whatsapp.data = Uri.parse(url)
            startActivity(whatsapp)

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