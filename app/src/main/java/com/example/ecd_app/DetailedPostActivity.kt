package com.example.ecd_app

import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*

class DetailedPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detailed_post)

        var intent = intent //getting the intent
        val postID = intent.getIntExtra("iPostID",0)
        val postTitle = intent.getStringExtra("iPostTitle")
        val postDateCreated = intent.getStringExtra("iPostDate")
        var postContent = intent.getStringExtra("iPostContent")
        val postMetaData = intent.getStringExtra("iPostMetaData")

//        postContent = "\n" +
//                "<div class=\"ct-section-inner-wrap\"><h1 id=\"headline-3-61\" class=\"ct-headline headerh\"><span id=\"span-4-61\" class=\"ct-span\">Storing Breast Milk from Breastfeeding</span></h1><div id=\"text_block-5-61\" class=\"ct-text-block\"><span id=\"span-6-61\" class=\"ct-span\">June 15, 2022</span></div><div id=\"div_block-7-61\" class=\"ct-div-block\"></div><div id=\"text_block-8-61\" class=\"ct-text-block\"><span id=\"span-9-61\" class=\"ct-span oxy-stock-content-styles\">\n" +
//                "    <figure class=\"wp-block-video\"><video controls=\"\" src=\"https://ecdportal.azurewebsites.net/wp-content/uploads/2022/06/storage.vid_.mp4\"></video></figure>\n" +
//                "    </span></div><div id=\"text_block-11-61\" class=\"ct-text-block\"><span id=\"ct-placeholder-12\"></span></div><div id=\"code_block-15-61\" class=\"ct-code-block\"><script type=\"text/javascript\">document.getElementById('section-2-61').style.display = 'unset';</script>\n" +
//                "    Current User: SuvanthSuv002<br><br>This content is assigned to you, SuvanthSuv002<br></div></div>"



        postContent= "<!-- wp:video {\\\"id\\\":74} -->\\n\" +\n" +
                "                    \"<figure class=\\\"wp-block-video\\\"><video controls src=\\\"https://ecdportal.azurewebsites.net/wp-content/uploads/2022/06/feedingtime_vid.mp4\\\" width=\"10\" height=\"10\"></video></figure>\\n\" +\n" +
                "                    \"<!-- /wp:video -->"
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading Data...")
        progressDialog.setCancelable(false)
        val web_view = findViewById<WebView>(R.id.web_view)
        web_view.requestFocus()
        web_view.settings.lightTouchEnabled = true
        web_view.settings.javaScriptEnabled = true
//        web_view.getSettings().setLoadWithOverviewMode(true);
//        web_view.getSettings().setUseWideViewPort(true);
        web_view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        web_view.settings.setGeolocationEnabled(true)
        web_view.isSoundEffectsEnabled = true
        if (postContent != null) {
            web_view.loadData(postContent, "text/html", "UTF-8")
        }
        web_view.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView, progress:Int) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss()}
            }
        }

    }
}