package com.example.ecd_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_ARTICLE_FNAME= "EXTRA_ARTICLE_FNAME"
class ArticleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        supportActionBar?.title = "Articles"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //layout
        //adapter
        val articleList : List<Article> = listOf(
            Article("Road To Health", "Road to health government book","rthb_booklet.pdf"),
            Article("RTHB Guide","Guide for using the RTHB Booklet","rthb_guide.pdf"),//new article
            Article("Road To Health Girls","Road to health government book for girls","rthb_girls.pdf"),
            Article("Road To Health Boys","Road to health government book for boys","rthb_boys.pdf"),
            Article("Side-By-Side Breastfeeding","Outlines what you should know about breastfeeding","breastfeeding-qa-booklet.pdf"),
            Article("Awareness for Breastfeeding","Breastfeeding poster","poster.pdf"),
            Article("COMACH Poster","Comach design poster","comach.pdf"),
            Article("1000 Days","Unicef 1000 Days","unicef1000days.pdf"),
            Article("1000 Days Poster","1000 Day Poster graphic","1000daysposter.pdf"),
            Article("Neonatal Care","Unicef neonatal care guide","unicef_neonatal.pdf"),
            Article("Importance Of Play","Article describing the process of learning through play","learn_through_play.pdf"),
            //new articles
            Article("Child Immunization","Immunization information poster","childimmunizationsposter.pdf"),
            Article("Messages for Mothers","Afrikaans m4m brochure","m4mafrikaansbrochure.pdf"),
            Article("Messages for Mothers","isiXhosa m4m brochure","m4misixhosabrochure.pdf"),
            Article("Mental Health for Mothers M4M","Mental health for mothers","m4mmentalhealthdocument.pdf"),
            Article("Physical Health for Mothers M4M","Physical health for mothers","m4mphysicalhealthdocument.pdf"),
            )
        val rvArticles : RecyclerView = findViewById(R.id.rvArticle)
        rvArticles.apply {
            layoutManager=LinearLayoutManager(this@ArticleListActivity)
            adapter = ArticleAdapter(articleList, object: ArticleAdapter.OnClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@ArticleListActivity, ArticleActivity::class.java)
                    val articleName = articleList[position]
                    intent.putExtra(EXTRA_ARTICLE_FNAME, articleName)
                    startActivity(intent)

                }
            })
        }
    }
}