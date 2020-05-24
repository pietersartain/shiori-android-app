package com.example.shiori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_article_list_item.view.*
import org.json.JSONArray

class ArticleAdapter(val items : JSONArray, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    // Gets the number of articles in the list
    override fun getItemCount(): Int {
        return items.length()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_article_list_item, parent, false))
    }

    // Binds each article in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items.getJSONObject(position)

        Picasso.get()
            .load(prefs.getString("server","<unset>") + article.getString("imageURL"))
            .resize(50, 50)
            .centerCrop()
            .into(holder.ivImage)

        holder.tvTitle.text = article.getString("title")

        val excerpt = article.getString("excerpt")
        holder.tvExcerpt.text = if (excerpt.length > 150) excerpt.take(150) + "..." else excerpt

        val tagsArray = article.getJSONArray("tags")
        val tags = ArrayList<String>()
        for (i in 0 until tagsArray.length()) {
            tags.add(tagsArray.getJSONObject(i).getString("name"))
        }
        holder.tvTags.text = tags.joinToString()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each article to
    val ivImage: ImageView = view.row_image
    val tvTitle: TextView = view.row_title
    val tvExcerpt: TextView = view.row_excerpt
    val tvTags: TextView = view.row_tags
}