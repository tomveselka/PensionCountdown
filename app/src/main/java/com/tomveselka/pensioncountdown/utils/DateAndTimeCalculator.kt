package com.tomveselka.pensioncountdown.utils

import android.app.Application
import android.content.Context
import com.tomveselka.pensioncountdown.MainActivity
import com.tomveselka.pensioncountdown.R
import java.time.Duration
import java.time.LocalTime
import kotlin.math.ceil
import com.tomveselka.pensioncountdown.R.id.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class DateAndTimeCalculator{
    //https://stackoverflow.com/questions/42340687/two-or-more-plulars-in-one-string-in-android-xml
    //passing Context to access Resources
    fun calculateTime(context:Context, pensionReached: Boolean): String {
        if (pensionReached){
            val hoursString = context.resources.getQuantityString(R.plurals.number_of_hours, 0, 0)
            val minutesString = context.resources.getQuantityString(R.plurals.number_of_minutes, 0, 0)
            val secondsString = context.resources.getQuantityString(R.plurals.number_of_seconds, 0, 0)

            return String.format(context.resources.getString(R.string.hours,hoursString, minutesString,secondsString))
        }
        var diff = Duration.between(LocalTime.now(), LocalTime.MAX)

        var hours = diff.toHours()
        val hoursString = context.resources.getQuantityString(R.plurals.number_of_hours, hours.toInt(), hours)
        diff = diff.minusHours(hours);

        var minutes = diff.toMinutes()
        val minutesString = context.resources.getQuantityString(R.plurals.number_of_minutes, minutes.toInt(), minutes)
        diff = diff.minusMinutes(minutes);

        var seconds = ceil((diff.toMillis()/1000).toDouble()).toInt()
        val secondsString = context.resources.getQuantityString(R.plurals.number_of_seconds, seconds, seconds)

        return String.format(context.resources.getString(R.string.hours,hoursString, minutesString,secondsString))
    }

    fun calculateDate(context: Context, birthDateString: String, pensionAgeString: String): List<String>{
        var list = mutableListOf<String>()
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var birthDate = LocalDate.parse(birthDateString, formatter)

        if (pensionAgeString=="P"){
            val yearsString = context.resources.getQuantityString(R.plurals.number_of_years, 0, 0)
            val monthsString = context.resources.getQuantityString(R.plurals.number_of_months, 0, 0)
            val daysString = context.resources.getQuantityString(R.plurals.number_of_days, 0, 0)
            list.add(String.format(context.resources.getString(R.string.days,yearsString, monthsString,daysString)))
            list.add("P")
            return list
        }
        var pensionDate=LocalDate.now()
        if (pensionAgeString.length<4){
            pensionDate=birthDate.plusYears(pensionAgeString.substring(0,2).toLong())
        }else{
            pensionDate=birthDate.plusYears(pensionAgeString.substring(0,2).toLong())
            pensionDate=pensionDate.plusMonths(pensionAgeString.substring(4,5).toLong())
        }

        if (pensionDate<LocalDate.now()){
            val yearsString = context.resources.getQuantityString(R.plurals.number_of_years, 0, 0)
            val monthsString = context.resources.getQuantityString(R.plurals.number_of_months, 0, 0)
            val daysString = context.resources.getQuantityString(R.plurals.number_of_days, 0, 0)
            list.add(String.format(context.resources.getString(R.string.days,yearsString, monthsString,daysString)))
            list.add("P")
            return list
        }

        var yearsBetween = ChronoUnit.YEARS.between(LocalDate.now(), pensionDate)
        val yearsString = context.resources.getQuantityString(R.plurals.number_of_years, yearsBetween.toInt(), yearsBetween)

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
        val monthsString = context.resources.getQuantityString(R.plurals.number_of_months, monthsBetween.toInt(), monthsBetween)

        var daysBetween = (pensionDate.minusYears(yearsBetween).minusMonths(monthsBetween).toEpochDay() - LocalDate.now().toEpochDay())
        val daysString = context.resources.getQuantityString(R.plurals.number_of_days, daysBetween.toInt(), daysBetween)
        list.add(String.format(context.resources.getString(R.string.days,yearsString, monthsString,daysString)))
        list.add("F")
        return list
    }
}