package com.tomveselka.pensioncountdown

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioGroup
import androidx.appcompat.widget.Toolbar
import com.tomveselka.pensioncountdown.preferences.PreferencesHandler
import com.tomveselka.pensioncountdown.utils.PensionAgeCalculator
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class UserDataInputActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var genderRadioGroup: RadioGroup
    lateinit var genderRadioMale: RadioButton
    lateinit var genderRadioFemale: RadioButton
    lateinit var dateOfBirthEdit: EditText
    lateinit var numberOfChildrenLabel: TextView
    lateinit var numberOfChildrenSpinner: AbsSpinner
    lateinit var calculateButton: Button
    lateinit var resultAgeAndDate: TextView
    var cal = Calendar.getInstance()
    private var pensionAgeCalculator = PensionAgeCalculator()
    private var preferencesHandler = PreferencesHandler()
    lateinit var pensionAge : String
    lateinit var dateOfBirth : String
    lateinit var gender : String
    lateinit var numberOfChildren : String

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("UserInput", "Entered user input activity")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_data_input)
        initializeViews()

        //DROPDOWN NUMBER OF CHILDREN
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.number_of_children,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            numberOfChildrenSpinner.adapter = adapter
        }

        //DATEPICKER DATE OF BIRTH
        //https://www.tutorialkart.com/kotlin-android/android-datepicker-kotlin-example/
        // create an OnDateSetListener
        dateOfBirthEdit.setText("--/--/----")
        dateOfBirthEdit.setShowSoftInputOnFocus(false);
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        dateOfBirthEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@UserDataInputActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        loadPreferences()

        var toolbar =findViewById<Toolbar>(R.id.mainToolbar)
        toolbar.title=getString(R.string.user_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        Log.i("UserInput","UserInputInitialised view")





        //GENDER RADIO
        genderRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group:RadioGroup, checkedId ->
            if (checkedId == R.id.genderRadioButtonMale) {
                Log.i("UserInput","Clicked male")
                numberOfChildrenLabel.visibility=View.GONE
                numberOfChildrenSpinner.visibility=View.GONE
            } else if (checkedId == R.id.genderRadioButtonFemale) {
                Log.i("UserInput","Clicked female")
                numberOfChildrenLabel.visibility=View.VISIBLE
                numberOfChildrenSpinner.visibility=View.VISIBLE
            }
        })

        //RESULT BUTTON
        calculateButton.setOnClickListener {
            numberOfChildren=numberOfChildrenSpinner.selectedItemPosition.toString()
            Log.i("UserInputResult","Number of children "+numberOfChildren)

            if (genderRadioGroup.checkedRadioButtonId == R.id.genderRadioButtonMale){
                gender = "M"
            }else{
                gender = "F"
            }
            Log.i("UserInputResult","Selected radiobutton "+gender)

            dateOfBirth = dateOfBirthEdit.text.toString()
            Log.i("UserInputResult","input date "+dateOfBirth)

            pensionAge = pensionAgeCalculator.calculatePension(gender,dateOfBirthEdit.text.toString(),numberOfChildrenSpinner.selectedItemPosition.toString()).toString()
            Log.i("UserInputResult","Pension age "+pensionAge)
            if (pensionAge=="P"){
                resultAgeAndDate.text=getString(R.string.reached_pension)
            }else {

                var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                var dateOfBirthDate = LocalDate.parse(dateOfBirthEdit.text, formatter)
                var pensionDate = LocalDate.now()
                if (pensionAge.length < 4) {
                    pensionDate = dateOfBirthDate.plusYears(pensionAge.substring(0, 2).toLong())
                } else {
                    pensionDate = dateOfBirthDate.plusYears(pensionAge.substring(0, 2).toLong())
                    pensionDate = pensionDate.plusMonths(pensionAge.substring(4, 5).toLong())
                }

                Log.i("UserInputResult", "Pension date " + pensionDate)
                val yearsString = resources.getQuantityString(R.plurals.number_of_years, pensionAge.substring(0, 2).toInt(), pensionAge.substring(0, 2).toLong())
                var monthsString=""
                if (pensionAge.length > 3) {
                    monthsString=resources.getQuantityString(R.plurals.and_number_of_months,pensionAge.substring(4, 5).toInt(),pensionAge.substring(4, 5).toLong())
                }
                resultAgeAndDate.text =
                    getString(R.string.resultAgeAndDate, yearsString,monthsString, pensionDate)
            }

            preferencesHandler.saveUserData(this, gender, dateOfBirth, numberOfChildren, pensionAge)
            Log.i("UserInputResult","Data saved to preferences: gender="+gender+" dateOfBirth="+dateOfBirth+" numberOfChildre="+numberOfChildren+" pensionAge="+pensionAge)
            resultAgeAndDate.visibility=View.VISIBLE
        }
    }



    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        Log.i("UserInput","Selected item "+parent.getItemAtPosition(pos).toString())
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }


    private fun initializeViews() {
        genderRadioGroup= findViewById<RadioGroup>(R.id.genderRadioGroup)
        genderRadioMale = findViewById<RadioButton>(R.id.genderRadioButtonMale)
        genderRadioFemale = findViewById<RadioButton>(R.id.genderRadioButtonFemale)
        dateOfBirthEdit = findViewById<EditText>(R.id.dateOfBirthInput)
        numberOfChildrenLabel = findViewById<TextView>(R.id.numberOfChildrenLabel)
        numberOfChildrenSpinner = findViewById<Spinner>(R.id.numberOfChildrenSpinner)
        calculateButton = findViewById<Button>(R.id.calculateButton)
        resultAgeAndDate = findViewById<TextView>(R.id.resultAgeAndDate)
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        dateOfBirthEdit.setText(sdf.format(cal.getTime()))
    }

    private fun loadPreferences(){
        var listOfPreference = preferencesHandler.getSavedUserData(this)
        gender = listOfPreference.get(0)
        dateOfBirth = listOfPreference.get(1)
        numberOfChildren = listOfPreference.get(2)
        pensionAge = listOfPreference.get(3)
        Log.i("UserInputResult","Data loaded from preferences: gender="+gender+" dateOfBirth="+dateOfBirth+" numberOfChildre="+numberOfChildren+" pensionAge="+pensionAge)
        setFieldsFromPreferences()
    }

    private fun setFieldsFromPreferences(){
        if (gender=="M"){
            genderRadioGroup.check(genderRadioMale.id)
            numberOfChildrenLabel.visibility=View.GONE
            numberOfChildrenSpinner.visibility=View.GONE
        }else{
            genderRadioGroup.check(genderRadioFemale.id)
            numberOfChildrenLabel.visibility=View.VISIBLE
            numberOfChildrenSpinner.visibility=View.VISIBLE
        }
        dateOfBirthEdit.setText(dateOfBirth)
        numberOfChildrenSpinner.setSelection(numberOfChildren.toInt())

    }
}