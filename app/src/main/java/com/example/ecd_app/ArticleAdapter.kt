package com.example.ecd_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Suvanth Ramruthen
 * ArticleAdapter drives the changes in the Article recyclerView. Holds all data related to article item
 *
 * @property articleList - list of articles to render
 * @property onClickListener - Listerner for article list item clicks
 */
class ArticleAdapter(val articleList: List<Article>, val onClickListener: OnClickListener) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    /**
     *retrieves position of item clicked in recyclerView
     */
    interface OnClickListener{
        fun onItemClick(position: Int)
    }


    lateinit var articleTitle: TextView;//title of article in thumbnail list item
    lateinit var articleDescription: TextView;//Article short description
    lateinit var articleImage: ImageView;


    /**
     *Creating the holder of the list items view, generates list items UI dynamically
     * @param parent parent of list item
     * @param viewType type of the view that is being held
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from((parent.context)).inflate(R.layout.article_item_layout, parent, false)
        return ViewHolder(layoutView)
    }

    /**
     * Binding the data to the generated list item view
     * @param holder view holder of list item
     * @param position position of item in article list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position] //retrieving article object
        articleTitle= holder.view.findViewById(R.id.tvArticleTitle) //finding UI component for Article Title
        articleDescription= holder.view.findViewById(R.id.tvArticleDescription) //finding UI component for Article Description
        articleTitle.text = article.articleTitle //setting UI component text for Article Title
        articleDescription.text= article.articleDescriptor //setting UI component text for Article description
        holder.itemView.setOnClickListener(){
            onClickListener.onItemClick(position)
        }
    }

    /**
     *Getting the item count in the list, aids in list ui generation
     * @return Article list size
     */
    override fun getItemCount(): Int {
       return articleList.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){//viewHolder for recyclerView

    }

}
