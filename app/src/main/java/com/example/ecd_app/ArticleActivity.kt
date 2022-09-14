package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView

/**
 * @author Suvanth Ramruthen
 * The ArticleActivity is the page responsible for rendering pdfs passed through intents
 */
class ArticleActivity : AppCompatActivity() {

    private lateinit var articleFile : Article// object initialised for Serialized article object received from intent

    /**
     * Overridden onCreate is called when ArticleActivty is created. This function edits the view
     * to display the data received in the intent put in from the Article List activity.
     * @param savedInstanceState bundle null safe save state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//enabling support action bar
        articleFile = intent.getSerializableExtra(EXTRA_ARTICLE_FNAME) as Article //retrieving serialized pdf extra from the intent
        Toast.makeText(this@ArticleActivity, "${articleFile.articleFileName}", Toast.LENGTH_LONG).show()//toast for file being displayed
        supportActionBar?.title = articleFile.articleTitle//setting support action bar title to pdf name
        val pdf: PDFView = findViewById(R.id.pdfView)//referencing xml component
        pdf.fromAsset(articleFile.articleFileName).enableSwipe(true).load()//swipe enabled
    }
}