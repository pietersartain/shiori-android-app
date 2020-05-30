package com.example.shiori

import org.json.JSONArray

data class Article(
    val imageUrl: String,
    val title: String,
    val excerpt: String,
    val tagsArray: JSONArray,
    val id: Int
)
{
    fun getTagString(): String {
        val tags = ArrayList<String>()
        for (i in 0 until tagsArray.length()) {
            tags.add(tagsArray.getJSONObject(i).getString("name"))
        }
        return tags.joinToString()
    }

    fun getExcerpt(size: Int): String {
        return if (excerpt.length > size) excerpt.take(size) + "..." else excerpt
    }
}