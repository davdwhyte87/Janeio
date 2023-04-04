package com.app.janeio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set action bar
        setContentView(R.layout.home_screen)
    }
}
