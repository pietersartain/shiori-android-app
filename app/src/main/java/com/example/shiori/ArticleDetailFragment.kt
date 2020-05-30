package com.example.shiori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_articledetail.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ArticleDetailFragment : Fragment() {

    private lateinit var api: ShioriApiViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        api = ViewModelProvider(this.requireActivity()).get(ShioriApiViewModel::class.java)
        val prefs = api.getPrefs()

        api.getSelectedArticle().observe(viewLifecycleOwner, Observer { articleId ->
            val articleUrl = "${prefs.getString("server","<unset>")}/bookmark/${articleId}/content"
            webview.loadUrl(articleUrl)
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articledetail, container, false)
    }
}
