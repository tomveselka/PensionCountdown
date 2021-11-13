package com.tomveselka.pensioncountdown

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tomveselka.pensioncountdown.preferences.PreferencesHandler
import com.tomveselka.pensioncountdown.utils.DateAndTimeCalculator


class MainActivity : AppCompatActivity() {
    var timerHandler = Handler(Looper.getMainLooper())
    private var dateAndTimeCalculator = DateAndTimeCalculator()
    private var preferencesHandler = PreferencesHandler()
    lateinit  var timeView : TextView
    lateinit var dateView : TextView
    //setting some default value for initial initialization of Handler
    var pensionAge = "65"
    var birthDate = "01/01/2000"


    var timerRunnable: Runnable = object : Runnable {
        override fun run() {
            setTimeRemaining ()
            setDaysRemaining ()
            timerHandler.postDelayed(this, 1000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadPreferences()

        //https://www.youtube.com/watch?v=eC1LpeaXT8Y
        var toolbar =findViewById<Toolbar>(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        val intent = Intent(this, UserDataInputActivity::class.java).apply {  }
        toolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (item.itemId==R.id.user_setting){
                    Log.i("MainActivity", "User setting selected")
                    startActivity(intent)
                    return true
                }
                if (item.itemId==R.id.about){
                    Log.i("MainActivity", "About selected")
                    return true
                }
                return false
            }
        })

        timeView = findViewById<TextView>(R.id.time_remaining)
        dateView = findViewById<TextView>(R.id.days_remaining)

        timerHandler.postDelayed(timerRunnable, 0);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    private fun setTimeRemaining (){
        timeView.setText(dateAndTimeCalculator.calculateTime(this))
    }

    private fun setDaysRemaining(){
        dateView.setText(dateAndTimeCalculator.calculateDate(this,birthDate,pensionAge))
    }

    private fun loadPreferences(){
        var listOfPreference = preferencesHandler.getSavedUserData(this)
        birthDate = listOfPreference.get(1)
        pensionAge = listOfPreference.get(3)

    }
}