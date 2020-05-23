package com.example.shiori

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_articlelist.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArticleListFragment : Fragment() {

    private lateinit var api: ShioriApiViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        api = this.activity?.let { ViewModelProviders.of(it).get(ShioriApiViewModel::class.java) }!!

        api.getSession().observe(viewLifecycleOwner, Observer { api.bookmarkRequest(1) })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articlelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        api.getBookmarks().observe(viewLifecycleOwner, Observer { item ->
            // Update the UI
            // Access the RecyclerView Adapter and load the data into it
            recyclerView.adapter = BookmarkAdapter(item, this.context as Context)
        })

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_ArticleListFragment_to_ArticleDetailFragment)
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)

        view.findViewById<Button>(R.id.button_settings).setOnClickListener {
            view ->

            Snackbar.make(view,
//                "Replace with your own action",
                "Server: ${prefs.getString("server","<unset>")}",
                Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
//        }
        }
    }

}
