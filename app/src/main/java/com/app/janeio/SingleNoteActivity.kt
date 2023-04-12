package com.app.janeio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar

class SingleNoteActivity : AppCompatActivity() {
    private lateinit var toolbar:androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_note)

        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.back_toolbar)
        setSupportActionBar(toolbar)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

//        menuInflater.inflate(R.menu.back_menu, menu)
//        return super.onCreateOptionsMenu(menu)
        val actionBar = supportActionBar
        if (actionBar != null) {
            Log.i("XXXX", "aactionbae")
            actionBar.setDisplayHomeAsUpEnabled(true)
            val actionBarView = layoutInflater.inflate(R.layout.action_bar_back, null)
            actionBar.setCustomView(actionBarView)
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        }
        return true
    }
}