package com.example.ecd_app

//import android.R
import android.app.ProgressDialog
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class postContentWebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_content_web_view)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading Data...")
        progressDialog.setCancelable(false)
        val web_view = findViewById<WebView>(R.id.web_view)
        web_view.requestFocus()
        web_view.settings.lightTouchEnabled = true
        web_view.settings.javaScriptEnabled = true
        web_view.settings.setGeolocationEnabled(true)
        web_view.isSoundEffectsEnabled = true
        web_view.loadData(
            "<!-- wp:paragraph -->\n" +
                    "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vehicula in lectus non accumsan. Praesent at dui ante. Vestibulum eget leo vitae mi venenatis tristique ut non quam. Donec dictum risus purus, et porttitor ex vestibulum sit amet. Sed sed nibh eros. Donec blandit sodales libero id fringilla. Suspendisse aliquam efficitur volutpat. Praesent blandit metus id ipsum condimentum egestas. Integer quis sagittis eros. Duis posuere, purus suscipit molestie venenatis, nibh nunc aliquet dolor, id consequat nulla tortor vitae sem.</p>\n" +
                    "<!-- /wp:paragraph -->\n" +
                    "\n" +
                    "<!-- wp:paragraph -->\n" +
                    "<p>Suspendisse blandit auctor dui eu tincidunt. Vestibulum sit amet nunc ac risus pretium dignissim. Praesent in bibendum ligula. Sed porta tortor vitae libero viverra ultricies. Morbi non sem mauris. Sed tempor tincidunt eros non rhoncus. Aenean nec efficitur ante. Fusce laoreet, est id feugiat porta, dui lacus efficitur odio, eget pulvinar dolor nibh et nunc. Suspendisse dictum sapien vulputate libero gravida ullamcorper. Sed nec nisl a nibh tempor tristique. Integer porta tincidunt mollis. Suspendisse ac hendrerit nunc. Integer ut dui turpis. Morbi vel fringilla turpis. Nam tincidunt, quam sit amet interdum consectetur, dolor quam dapibus nisi, vitae dapibus ipsum dolor et nunc. Donec lacinia a justo vel sagittis.</p>\n" +
                    "<!-- /wp:paragraph -->\n" +
                    "\n" +
                    "<!-- wp:paragraph -->\n" +
                    "<p>Morbi fermentum, tortor sed condimentum semper, justo velit hendrerit justo, sodales porta lectus quam vel dolor. Fusce accumsan pellentesque sem ut accumsan. Nam non fringilla elit. Duis et euismod neque. Fusce malesuada arcu sit amet orci vehicula consequat. Donec interdum, est vel lacinia gravida, dolor elit congue est, in semper tortor tellus in felis. Morbi nisi sem, consectetur eget suscipit vel, semper sit amet tellus. Quisque accumsan nisi non aliquam rhoncus. Sed fringilla nibh ultrices felis egestas, eu ultricies nulla maximus. Aliquam nec luctus erat. Aenean metus augue, scelerisque at dolor non, vestibulum efficitur lectus.</p>\n" +
                    "<!-- /wp:paragraph -->\n" +
                    "\n" +
                    "<!-- wp:paragraph -->\n" +
                    "<p>Aenean pretium diam ac diam laoreet imperdiet. Vestibulum eget nisi blandit leo feugiat ultrices. Integer tristique hendrerit arcu, vitae rutrum sapien venenatis at. Duis in tellus nulla. Suspendisse aliquet diam sed nunc tincidunt, vel lacinia est pretium. Morbi id bibendum metus. Aliquam gravida interdum enim ut tristique.</p>\n" +
                    "<!-- /wp:paragraph -->\n" +
                    "\n" +
                    "<!-- wp:paragraph -->\n" +
                    "<p>Etiam et ligula in magna rhoncus ullamcorper. Cras nec accumsan nisl, sit amet posuere odio. Nunc volutpat sem non dolor lobortis, ut sodales orci placerat. Nullam non nibh maximus, euismod lorem in, aliquet tortor. Aliquam non nisl eu mi ultrices egestas at non magna. Praesent sagittis vestibulum nunc semper egestas. Nullam lectus nisi, vulputate eget libero at, sollicitudin sodales est. Nulla tincidunt mi eu iaculis tempor. Duis pellentesque feugiat diam vel mollis. Morbi scelerisque dictum interdum. Integer hendrerit varius odio, eget congue eros ullamcorper sit amet.</p>\n" +
                    "<!-- /wp:paragraph -->\n" +
                    "\n" +
                    "<!-- wp:image {\"id\":42,\"sizeSlug\":\"full\",\"linkDestination\":\"none\"} -->\n" +
                    "<figure class=\"wp-block-image size-full\"><img src=\"https://ecdportal.azurewebsites.net/wp-content/uploads/2022/06/BBP-Logo.png\" alt=\"BBP Logo\" class=\"wp-image-42\"/></figure>\n" +
                    "<!-- /wp:image -->",
            "text/html", "UTF-8"
        )
        web_view.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView, progress:Int) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                progressDialog.dismiss();
            }
            }
        }
    }
}