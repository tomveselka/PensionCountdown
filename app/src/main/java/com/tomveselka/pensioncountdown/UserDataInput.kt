package com.tomveselka.pensioncountdown

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioGroup

import android.R.string.no




class UserDataInput : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var genderRadioGroup: RadioGroup
    lateinit var genderRadioMale: RadioButton
    lateinit var genderRadioFemale: RadioButton
    lateinit var dateOfBirthEdit: EditText
    lateinit var numberOfChildrenLabel: TextView
    lateinit var numberOfChildrenSpinner: AbsSpinner
    lateinit var calculateButton: Button
    lateinit var resultAgeAndDate: TextView
    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("UserInput", "Entered user input activity")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_data_input)
        initializeViews()


        Log.i("UserInput","UserInputInitialised view")

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
                DatePickerDialog(this@UserDataInput,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        genderRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group:RadioGroup, checkedId ->
            if (checkedId == R.id.genderRadioButtonMale) {
                Log.i("UserInput","Clicked male")
            } else if (checkedId == R.id.genderRadioButtonFemale) {
                Log.i("UserInput","Clicked female")
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        Log.i("UserInput","Selected item "+parent.getItemAtPosition(pos).toString())
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun calculateButtonClicked(view: View) {

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
}