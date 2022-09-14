package com.example.ecd_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_ARTICLE_FNAME= "EXTRA_ARTICLE_FNAME"

/**
 * Activity that holds the Article Recycler View List
 */
class ArticleListActivity : AppCompatActivity() {

    /**
     * Creates view and list for ArticleList
     * @param savedInstanceState saved state of bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        supportActionBar?.title = "Articles" //Setting support actionbar title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val articleList : List<Article> = listOf(//geenrating list of articles
            Article("Road To Health", "Road to health government book","rthb_booklet.pdf"),
            Article("RTHB Guide","Guide for using the RTHB Booklet","rthb_guide.pdf"),//new article
            Article("Side-By-Side Breastfeeding","Outlines what you should know about breastfeeding","breastfeeding-qa-booklet.pdf"),
            Article("1000 Days Poster","1000 Day Poster graphic","1000daysposter.pdf"),
            Article("Importance Of Play","Article describing the process of learning through play","learn_through_play.pdf"),
            Article("Child Immunization","Immunization information poster","childimmunizationsposter.pdf"),
            Article("Messages for Mothers","Afrikaans m4m brochure","m4mafrikaansbrochure.pdf"),
            Article("Messages for Mothers","isiXhosa m4m brochure","m4misixhosabrochure.pdf"),
            Article("Mental Health for Mothers M4M","Mental health for mothers","m4mmentalhealthdocument.pdf"),
            Article("Physical Health for Mothers M4M","Physical health for mothers","m4mphysicalhealthdocument.pdf"),
            )
        val rvArticles : RecyclerView = findViewById(R.id.rvArticle) //recyclerView Ui component reference
        rvArticles.apply {
            layoutManager=LinearLayoutManager(this@ArticleListActivity) //setting layout manager
            adapter = ArticleAdapter(articleList, object: ArticleAdapter.OnClickListener{
                override fun onItemClick(position: Int) {//setting click listener
                    val intent = Intent(this@ArticleListActivity, ArticleActivity::class.java)//creating intent to pass article details
                    val articleName = articleList[position]
                    intent.putExtra(EXTRA_ARTICLE_FNAME, articleName) //putting article file name extra
                    startActivity(intent)
                }

            })

        }

    }
}