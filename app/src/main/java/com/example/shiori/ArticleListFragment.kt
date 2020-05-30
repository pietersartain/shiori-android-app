package com.example.shiori

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_articlelist.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArticleListFragment : Fragment(), OnItemClickListener {

    private lateinit var api: ShioriApiViewModel
    private var currentVisiblePosition: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        api = ViewModelProvider(this.requireActivity()).get(ShioriApiViewModel::class.java)

        api.getSession().observe(viewLifecycleOwner, Observer {
            api.requestBookmarkPage(1)
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articlelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        api.getBookmarks().observe(viewLifecycleOwner, Observer { item ->
            // Update the UI
            // Access the RecyclerView Adapter and load the data into it
            recyclerView.adapter = ArticleAdapter(item, this.context as Context, this)
            // Set the scroll position to the last visible item seen onPause
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(currentVisiblePosition)
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val loadedPages = api.getBookmarkPages().value
                    if (loadedPages != null) {
                        val lastVisibleItem =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        if (!api.requestingBookmarks().value!! && (lastVisibleItem + 5 > loadedPages * 30)) {
                            api.requestBookmarkPage(loadedPages + 1)
                            currentVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        }
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        currentVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    override fun onItemClicked(article: Article) {
        api.setSelectedArticle(article.id)
        findNavController().navigate(R.id.action_ArticleListFragment_to_ArticleDetailFragment)
    }
}
