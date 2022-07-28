package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView

class ArticleActivity : AppCompatActivity() {

    private lateinit var articleFile : Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        articleFile = intent.getSerializableExtra(EXTRA_ARTICLE_FNAME) as Article
        Toast.makeText(this@ArticleActivity, "${articleFile.articleFileName}", Toast.LENGTH_LONG).show()
//        supportActionBar?.title = articleFile.articleTitle
        val pdf: PDFView = findViewById(R.id.pdfView)
        pdf.fromAsset(articleFile.articleFileName).enableSwipe(true).swipeHorizontal(true).load()
    }
}