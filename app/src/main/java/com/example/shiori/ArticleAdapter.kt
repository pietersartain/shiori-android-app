package com.example.shiori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_article_list_item.view.*
import org.json.JSONArray

class ArticleAdapter(val items : JSONArray, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of articles in the list
    override fun getItemCount(): Int {
        return items.length()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_article_list_item, parent, false))
    }

    // Binds each article title in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items.getJSONObject(position).getString("title")
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each article to
    val tvTitle: TextView = view.tv_article_title
}