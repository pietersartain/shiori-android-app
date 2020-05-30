package com.example.shiori

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ShioriApiViewModel(application: Application) : AndroidViewModel(application) {

    private val ctx: Context = application.applicationContext
    private val conn: ConnectionExtensions = ConnectionExtensions.getInstance(ctx)
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx)

    private val session = MutableLiveData<String>()
    private val errmsg = MutableLiveData<String>()
    private val bookmarks = MutableLiveData<ArrayList<Article>>()

    private val bookmarkPages = MutableLiveData<Int>()

    private val requestingBookmarks = MutableLiveData(false)

    private val selectedArticle = MutableLiveData<Int>()

    fun getPrefs(): SharedPreferences {
        return prefs
    }

    fun getSession(): LiveData<String> {
        return session
    }

    fun getBookmarks(): LiveData<ArrayList<Article>> {
        return bookmarks
    }

    fun requestingBookmarks(): LiveData<Boolean> {
        return requestingBookmarks
    }

    fun getBookmarkPages(): LiveData<Int> {
        return bookmarkPages
    }

    fun setSelectedArticle(articleId: Int) {
        selectedArticle.value = articleId
    }

    fun getSelectedArticle(): LiveData<Int> {
        return selectedArticle
    }

    fun logDebug(msg: String) {
        Log.d(this.javaClass.toString(), msg)
    }

    fun loggedIn(): Boolean {
        session.value ?: return false
        return true
    }

    fun doLogout() {

        // Create logout URL
        val logoutUrl = "${prefs.getString("server","<unset>")}/api/logout"

        // Send login request
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST, logoutUrl, null,
            Response.Listener {
                // Do something with the response
                session.postValue("")
            },
            Response.ErrorListener { error ->
                // Handle error
                errmsg.value = "Logout error: %s".format(error.toString())
                logDebug(errmsg.value!!)
            })
            {
            override fun getHeaders(): Map<String, String> {
                // Create HashMap of your Headers as the example provided below

                val headers = HashMap<String, String>()
                headers["X-Session-Id"] = session.value!!
                return headers
            }
        }

        // Run request
        conn.addToRequestQueue(jsonObjectRequest)
    }

    fun doLogin() {

        // Create login URL
        val loginUrl = "${prefs.getString("server","<unset>")}/api/login"

        // Create JSON body
        val body = JSONObject("{ username: \"${prefs.getString("username","unset")}\"," +
                "password: \"${prefs.getString("passwd","unset")}\" }")

        // Send login request
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, loginUrl, body,
            Response.Listener { response ->
                // Do something with the response
                CookieManager.getInstance().setCookie(prefs.getString("server","unset"), "session-id=${response.getString("session")}")
                CookieManager.getInstance().flush()
                this.session.value = response.getString("session")
            },
            Response.ErrorListener { error ->
                // Handle error
                errmsg.value = "Logout error: %s".format(error.toString())
                logDebug(errmsg.value!!)
            })

        // Run request
        conn.addToRequestQueue(jsonObjectRequest)
    }

    fun requestBookmarkPage(page: Int) {
        requestingBookmarks.value = true

        // Create URL
        val bookmarksUrl = "${prefs.getString("server","<unset>")}/api/bookmarks?page=${page}"

        // Send login request
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, bookmarksUrl, null,
            Response.Listener { response ->
                // Load response into a data object

                val bookmarkArray = response.getJSONArray("bookmarks")
                if (bookmarks.value.isNullOrEmpty()) {
                    bookmarks.value = ArrayList()
                }

                for (i in 0 until bookmarkArray.length()) {
                    val obj = bookmarkArray.getJSONObject(i)

                    val article = Article(
                        obj.getString("imageURL"),
                        obj.getString("title"),
                        obj.getString("excerpt"),
                        obj.getJSONArray("tags"),
                        obj.getInt("id")
                    )
                    bookmarks.value?.add(article)
                }
                bookmarks.postValue(bookmarks.value)
                bookmarkPages.value = page
                requestingBookmarks.value = false
            },
            Response.ErrorListener { error ->
                // Handle error
                errmsg.value = "Error retrieving bookmark list: %s".format(error.toString())
                logDebug(errmsg.value!!)
            })
        {
            // https://stackoverflow.com/a/54251710
            // Providing Request Headers

            override fun getHeaders(): Map<String, String> {
                // Create HashMap of your Headers as the example provided below

                val headers = HashMap<String, String>()
                headers["X-Session-Id"] = session.value!!
                return headers
            }
        }

        // Run request
        conn.addToRequestQueue(jsonObjectRequest)
    }

}