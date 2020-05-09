package com.example.shiori

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var conn: ConnectionExtensions
    private lateinit var prefs: SharedPreferences
    private lateinit var session: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /* Function hook for
         *
         */
        fab.setOnClickListener {
            onActionButtonClickDoTheThings()
        }

        this.prefs = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        this.conn = ConnectionExtensions.getInstance(this.applicationContext)
        this.session = doLogin()

    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
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
             {
//              Transition to the SettingsActivity
                val intent = Intent(this.applicationContext, SettingsActivity::class.java )
                startActivity(intent)
                true
             }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun doLogin(): String {

        // Create login URL
        val login_url = "${prefs.getString("server","<unset>")}/api/login"

        // Create JSON body
        val body = JSONObject("{ username: \"${prefs.getString("username","unset")}\"," +
                "password: \"${prefs.getString("passwd","unset")}\" }")

        var session: String = ""

        // Send login request
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, login_url, body,
            Response.Listener<JSONObject> { response ->
                // Do something with the response
                Snackbar.make(fab,
                    "Now logged in!",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
                session = response.getString("session")
            },
            Response.ErrorListener { error ->
                // Handle error
                Snackbar.make(fab,
                    "Login error: %s".format(error.toString()),
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            })

        // Run request
        conn.addToRequestQueue(jsonObjectRequest)

        return session
    }

    fun getBookmarks(page: Int): Boolean {

        // Create login URL
        val bookmarks_url = "${prefs.getString("server","<unset>")}/api/bookmarks"

        // Create JSON body
        val body = JSONObject("{ page: ${page}}")

        // Send login request
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, bookmarks_url, body,
            Response.Listener<JSONObject> { response ->
                // Do something with the response
                Snackbar.make(fab,
                    "Yay bookmarks",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            },
            Response.ErrorListener { error ->
                // Handle error
                Snackbar.make(fab,
                    "Error retrieving bookmark list: %s".format(error.toString()),
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }) {
                // https://stackoverflow.com/a/54251710
                // Providing Request Headers

                override fun getHeaders(): Map<String, String> {
                    // Create HashMap of your Headers as the example provided below

                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["X-Session-ID"] = session
                    return headers
                }
            }

        // Run request
        conn.addToRequestQueue(jsonObjectRequest)

        return true
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
