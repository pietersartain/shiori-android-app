package com.example.shiori

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var prefs: SharedPreferences
    private lateinit var api: ShioriApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar / navController
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfiguration)

        api = ViewModelProviders.of(this).get(ShioriApiViewModel::class.java)

        /* Function hook for the email button
         *
         */
        fab.setOnClickListener {
            {}
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        api.doLogin()
//        api.getBookmarks(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        api.doLogout()
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
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
