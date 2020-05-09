package com.example.shiori

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_articlelist.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArticleListFragment : Fragment() {

    // Initializing an empty ArrayList to be filled with animals
    val animals: ArrayList<String> = ArrayList()
//    private var listState: Parcelable? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articlelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Loads animals into the ArrayList
        addAnimals()

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        recyclerView.adapter = AnimalAdapter(animals, this.context as Context)

        // reload the listState (eg scroll position)
//        (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(listState)

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

    // Adds animals to the empty animals ArrayList
    fun addAnimals() {
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")
        animals.add("hamster")
        animals.add("bear")
        animals.add("lion")
        animals.add("tiger")
        animals.add("horse")
        animals.add("frog")
        animals.add("fish")
        animals.add("shark")
        animals.add("turtle")
        animals.add("elephant")
        animals.add("cow")
        animals.add("beaver")
        animals.add("bison")
        animals.add("porcupine")
        animals.add("rat")
        animals.add("mouse")
        animals.add("goose")
        animals.add("deer")
        animals.add("fox")
        animals.add("moose")
        animals.add("buffalo")
        animals.add("monkey")
        animals.add("penguin")
        animals.add("parrot")

    }
}
