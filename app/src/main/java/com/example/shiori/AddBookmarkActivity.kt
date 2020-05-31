package com.example.shiori

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration

class AddBookmarkActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var prefs: SharedPreferences
    private lateinit var api: ShioriApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbookmark)

        val tv = findViewById<View>(R.id.textView) as TextView
        val bookmarkURL = intent.getStringExtra(Intent.EXTRA_TEXT)

        tv.text = "Now adding ${bookmarkURL} to bookmarks ..."

        api = ViewModelProvider(this).get(ShioriApiViewModel::class.java)

        api.doLogin()

        api.getSession().observe(this, Observer {
            // api.requestBookmarkPage(1)
            api.saveBookmark(bookmarkURL)
        })

        api.savingBookmark().observe(this, Observer {
            if (!api.savingBookmark().value!!) {
                tv.text = "Done! Bye!"
                super.onBackPressed()
            }
        })

        api.errmsg.observe(this, Observer {
            tv.text = api.errmsg.value
        })
    }

    override fun onDestroy() {
        api.doLogout()
        super.onDestroy()
    }
}