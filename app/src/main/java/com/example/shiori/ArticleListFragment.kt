package com.example.shiori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArticleListFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articlelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_ArticleListFragment_to_ArticleDetailFragment)
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)

        view.findViewById<Button>(R.id.button_settings).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_settingsFragment)
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
