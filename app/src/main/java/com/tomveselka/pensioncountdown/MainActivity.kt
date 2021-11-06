package com.tomveselka.pensioncountdown

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tomveselka.pensioncountdown.utils.DateAndTimeCalculator


class MainActivity : AppCompatActivity() {
    var timerHandler = Handler(Looper.getMainLooper())
    private var dateAndTimeCalculator = DateAndTimeCalculator()
    lateinit  var timeView : TextView
    lateinit var dateView : TextView


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
        timeView = findViewById<TextView>(R.id.time_remaining)
        dateView = findViewById<TextView>(R.id.days_remaining)

        timerHandler.postDelayed(timerRunnable, 0);

    }


    private fun setTimeRemaining (){
        timeView.setText(dateAndTimeCalculator.calculateTime(this))
    }

    private fun setDaysRemaining(){
        dateView.setText(dateAndTimeCalculator.calculateDate(this))
    }

}