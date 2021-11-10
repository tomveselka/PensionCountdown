package com.tomveselka.pensioncountdown

import android.app.DatePickerDialog
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioGroup
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
                DatePickerDialog(this@UserDataInputActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

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
            Log.i("UserInputResult","Number of children "+numberOfChildrenSpinner.selectedItemPosition)
            var gender="male"
            if (genderRadioGroup.checkedRadioButtonId == R.id.genderRadioButtonMale){
                var gender = "male"
            }else{
                var gender = "female"
            }
            Log.i("UserInputResult","Selected radiobutton "+gender)
            Log.i("UserInputResult","input date "+dateOfBirthEdit.text)
            var pensionAge = pensionAgeCalculator.calculatePension(gender,dateOfBirthEdit.text.toString(),numberOfChildrenSpinner.selectedItemPosition.toString())
            Log.i("UserInputResult","Pension age "+pensionAge)
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var birthDate = LocalDate.parse(dateOfBirthEdit.text, formatter)
            var pensionDate=birthDate.plusYears(pensionAge.toLong())
            Log.i("UserInputResult","Pension age "+pensionDate)
            resultAgeAndDate.text=getString(R.string.resultAgeAndDate,pensionAge,pensionDate)
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
}