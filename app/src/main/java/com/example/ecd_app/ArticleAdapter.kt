package com.example.ecd_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(val articleList: List<Article>, val onClickListener: OnClickListener) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    interface OnClickListener{
        fun onItemClick(position: Int)
    }


    lateinit var articleTitle: TextView;
    lateinit var articleDescription: TextView;
    lateinit var articleImage: ImageView;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from((parent.context)).inflate(R.layout.article_item_layout, parent, false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        articleTitle= holder.view.findViewById(R.id.tvArticleTitle)
        articleDescription= holder.view.findViewById(R.id.tvArticleDescription)
        articleTitle.text = article.articleTitle
        articleDescription.text= article.articleDescriptor
        holder.itemView.setOnClickListener(){
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }

}
