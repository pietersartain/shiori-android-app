package com.example.shiori

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_article_list_item.view.*

class ArticleAdapter(private val items : ArrayList<Article>, private val context: Context, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ViewHolder>() {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    // Gets the number of articles in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_article_list_item, parent, false))
    }

    // Binds each article in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items[position]
        holder.bind(article, prefs, itemClickListener)
    }
}

class ViewHolder (private val view: View) : RecyclerView.ViewHolder(view) {
    private val ivImage: ImageView = view.row_image
    private val tvTitle: TextView = view.row_title
    private val tvExcerpt: TextView = view.row_excerpt
    private val tvTags: TextView = view.row_tags

    fun bind(article: Article, prefs: SharedPreferences, clickListener: OnItemClickListener)
    {
        Picasso.get()
            .load(prefs.getString("server","<unset>")!! + article.imageUrl)
            .resize(50, 50)
            .centerCrop()
            .into(ivImage)

        tvTitle.text = article.title
        tvExcerpt.text = article.getExcerpt(150)
        tvTags.text = article.getTagString()

        view.setOnClickListener {
            clickListener.onItemClicked(article)
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(article: Article)
}
