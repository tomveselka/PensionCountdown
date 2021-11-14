package com.tomveselka.pensioncountdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AboutApp", "Entered about application activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var toolbar =findViewById<Toolbar>(R.id.mainToolbar)
        toolbar.title=getString(R.string.about_app_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}