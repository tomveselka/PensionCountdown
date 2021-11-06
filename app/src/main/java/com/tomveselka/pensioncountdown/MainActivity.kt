package com.tomveselka.pensioncountdown

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.math.ceil
import com.tomveselka.pensioncountdown.R.id.*
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
        /*
        var diff = Duration.between(LocalTime.now(), LocalTime.MAX)

        var hours = diff.toHours()
        val hoursString = resources.getQuantityString(R.plurals.number_of_hours, hours.toInt(), hours)
        diff = diff.minusHours(hours);

        var minutes = diff.toMinutes()
        val minutesString = resources.getQuantityString(R.plurals.number_of_minutes, minutes.toInt(), minutes)
        diff = diff.minusMinutes(minutes);

        var seconds = ceil((diff.toMillis()/1000).toDouble()).toInt()
        val secondsString = resources.getQuantityString(R.plurals.number_of_seconds, seconds, seconds)

         */
        //timeView.setText(String.format(resources.getString(R.string.hours,hoursString, minutesString,secondsString)))
        timeView.setText(dateAndTimeCalculator.calculateTime(this))

    }

    private fun setDaysRemaining(){
        /*
        var birthDate = LocalDate.of(1969,11,28)
        var pensionDate = birthDate.plusYears(65)

        var yearsBetween = ChronoUnit.YEARS.between(LocalDate.now(), pensionDate)
        val yearsString = resources.getQuantityString(R.plurals.number_of_years, yearsBetween.toInt(), yearsBetween)

        var monthsBetween =  0L
        if (LocalDate.now().month==birthDate.month){
            monthsBetween = 0L
        }else if (LocalDate.now().monthValue<birthDate.monthValue) {
            monthsBetween = (birthDate.monthValue-LocalDate.now().monthValue).toLong()
        }else{
            monthsBetween = ChronoUnit.MONTHS.between(
                LocalDate.now(),
                birthDate.withYear(LocalDate.now().plusYears(1).year)
            )
        }
        val monthsString = resources.getQuantityString(R.plurals.number_of_months, monthsBetween.toInt(), monthsBetween)

        var daysBetween = (pensionDate.minusYears(yearsBetween).minusMonths(monthsBetween).toEpochDay() - LocalDate.now().toEpochDay())
        val daysString = resources.getQuantityString(R.plurals.number_of_days, daysBetween.toInt(), daysBetween)
        */
        //https://stackoverflow.com/questions/42340687/two-or-more-plulars-in-one-string-in-android-xml
        dateView.setText(dateAndTimeCalculator.calculateDate(this))

    }

}