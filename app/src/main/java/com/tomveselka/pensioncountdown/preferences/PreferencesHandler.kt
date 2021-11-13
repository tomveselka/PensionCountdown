package com.tomveselka.pensioncountdown.preferences

import android.content.Context
import android.util.Log
import com.tomveselka.pensioncountdown.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PreferencesHandler {
    fun saveUserData(context:Context, gender: String, dateOfBirth: String, numberOfChildren:String, pensionAge:String){
        val sharedPreferences=context.getSharedPreferences((context.resources.getString(R.string.preference_file_key)), Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(context.resources.getString(R.string.preference_file_gender),gender)
            .putString(context.resources.getString(R.string.preference_file_birthDate),dateOfBirth)
            .putString(context.resources.getString(R.string.preference_file_numberOfChildren),numberOfChildren)
            .putString(context.resources.getString(R.string.preference_file_pensionAge),pensionAge)
            .apply()
    }

    fun getSavedUserData(context:Context): List<String>{
        var list = mutableListOf<String>()
            val sharedPreferences=context.getSharedPreferences((context.resources.getString(R.string.preference_file_key)), Context.MODE_PRIVATE)
            val gender=sharedPreferences.getString(context.resources.getString(R.string.preference_file_gender), "M")
            Log.i("PreferencesHandler","Loaded gender successfully, gender="+gender)
            val sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var dateOfBirthDefault = LocalDate.now().format(sdf)
            Log.i("PreferencesHandler","Set up dateOfBirthDefault successfully, dateOfBirthDefault="+dateOfBirthDefault)
            val dateOfBirth=sharedPreferences.getString(context.resources.getString(R.string.preference_file_birthDate), dateOfBirthDefault)
            Log.i("PreferencesHandler","Loaded dateOfBirth successfully, dateOfBirth="+dateOfBirth)
            val numberOfChildren=sharedPreferences.getString(context.resources.getString(R.string.preference_file_numberOfChildren), "2")
            Log.i("PreferencesHandler","Loaded numberOfChildren successfully, numberOfChildren="+numberOfChildren)
            val pensionAge=sharedPreferences.getString(context.resources.getString(R.string.preference_file_pensionAge), "65")
             Log.i("PreferencesHandler","Loaded pensionAge successfully, pensionAge="+pensionAge)
            list.add(0, gender!!)
            list.add(1, dateOfBirth!!)
            list.add(2, numberOfChildren!!)
            list.add(3, pensionAge!!)
        return list
    }

}