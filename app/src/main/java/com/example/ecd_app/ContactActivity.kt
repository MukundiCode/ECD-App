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


/**
 * @author Suvanth Ramruthen
 * ContactActivity contains Bhabisanas story, carousel images with staff-parent interactions
 */
class ContactActivity : AppCompatActivity() {
    var imageArray: ArrayList<Int> = ArrayList()//image list to display
    lateinit var carouselView: CarouselView

    /**
     *onCreate function called to set view details
     * @param savedInstanceState saved bundle state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Information"
        val facebookImage : ImageView = findViewById(R.id.imageFacebook)
        val instagramImage : ImageView = findViewById(R.id.imageInstagram)
        val whatsappImage : ImageView = findViewById(R.id.imageWhatsapp)
        val ecdImage : ImageView = findViewById(R.id.imageBbpPortal)

        /**
         * Code to check if application package is installed on the phone
         * @param packageName name of package checked
         * @return true if installed, false otherwise
         */
        fun isAppInstalled(packageName: String): Boolean {
            val pm: PackageManager = getPackageManager()
            return try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)//getting info
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        /**
         * Redirect to Facebook
         */
        facebookImage.setOnClickListener(){
            val url: String = "https://www.facebook.com/bhabhisanababyproject/"
            val fb = Intent(Intent.ACTION_VIEW)
            fb.data = Uri.parse(url)
            startActivity(fb)
        }

        /**
         * Redirect to Instagram
         */
        instagramImage.setOnClickListener(){
            val url: String = "https://www.instagram.com/bhabhisanababyproject/?igshid=YmMyMTA2M2Y="
            val ig = Intent(Intent.ACTION_VIEW)
            ig.data = Uri.parse(url)
            startActivity(ig)

        }

        /**
         * Redirect to WhatsApp
         */
        whatsappImage.setOnClickListener(){
            val result = isAppInstalled("com.whatsapp")//checking if whatsapp is installed
            if(result){
                val url: String = "https://chat.whatsapp.com/HrqX8TOScucI2LDIUpEXvI"//Bhabisana group chat
                val whatsapp = Intent(Intent.ACTION_VIEW)
                whatsapp.data = Uri.parse(url)
                startActivity(whatsapp)
            }else{
                Toast.makeText(this@ContactActivity, "Install WhatsApp first", Toast.LENGTH_LONG).show()//showing whatsapp is not installed
            }



        }

        /**
         * Redirect to BBP webportal
         */
        ecdImage.setOnClickListener(){
            val url: String = "https://youandyourbaby.bhabhisana.org.za/"
            val ecd = Intent(Intent.ACTION_VIEW)
            ecd.data = Uri.parse(url)
            startActivity(ecd)

        }
        //Adding images to carousel
        imageArray.add(R.drawable.one)
        imageArray.add(R.drawable.two)
        imageArray.add(R.drawable.three)
        imageArray.add(R.drawable.four)

        carouselView=findViewById(R.id.carouselView)
        carouselView.pageCount=imageArray.size

        carouselView.setImageListener(imageListener)

    }
    //setting carouselView images
    var imageListener = ImageListener { position, imageView -> imageView.setImageResource(imageArray[position]) }
}