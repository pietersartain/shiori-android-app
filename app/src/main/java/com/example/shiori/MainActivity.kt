package com.example.shiori

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.android.volley.*
import com.android.volley.toolbox.*

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /* lambda expression type.
         * I think what we're doing is converting fab to view.
         * The first line view captures "this" as "view", then passes that into Snackbar.make()
         * Once we've got a Snackbar::view, we call .setAction.show() on it, to do a thing.
         */
//        val text = "None"
//        fab.setOnClickListener { view ->
//            Snackbar.make(view,
////                "Replace with your own action",
//                text,
//                Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        /* Partly unrolled function?
         *
         */
//        val text = onActionButtonClick()
//        val view = Snackbar.make(fab,
//            text,
//            Snackbar.LENGTH_LONG)
//
//        fab.setOnClickListener {
//            view.setAction("Action", null).show()
//        }

        /* Function hook for
         *
         */
        fab.setOnClickListener {
            onActionButtonClickDoTheThings()
        }

//        action_settings.setOnClickListener {}

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->

//            Somewhere else
//            {
//                toolbar.ctx.startActivity<SettingsActivity>()
//                true
//            }

//                Transition to a new Activity
                {
                val intent = Intent(this.applicationContext, SettingsActivity::class.java )
                startActivity(intent)
                true
                }

//                Load a new screen
//                {
//                setContentView(R.layout.settings_activity)
//                true
//                }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onActionButtonClick(): String {
//        val textView = findViewById<TextView>(R.id.text)
// ...

        var responseText = "None"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
//                textView.text = "Response is: ${response.substring(0, 500)}"
                responseText =  "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener {
//                textView.text = "That didn't work!"
                responseText = "That didn't work!"
                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

        return responseText
    }

    fun onActionButtonClickDoTheThings() {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Snackbar.make(fab,
                    "Response is: ${response.substring(0, 500)}",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            },
            Response.ErrorListener {
                Snackbar.make(fab,
                    "That didn't work!",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}
