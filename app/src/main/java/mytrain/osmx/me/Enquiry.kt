package mytrain.osmx.me

import android.os.Bundle
import android.view.View
import android.widget.*
import android.app.DatePickerDialog
import android.widget.EditText
import java.util.*

class Enquiry : BaseActivity() {
    val traveller = arrayOf("اختر عدد المسافرين",1,2,3,4)
    val travel_time = arrayOf("اختر توقيت السفر ","صباحاً","مساءاً")
    lateinit var et_travel_date: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquiry)
        et_travel_date = findViewById(R.id.et_travel_date)


        val s_travel_place =findViewById<Spinner>(R.id.s_travel_place)
        val s_arrive_place =findViewById<Spinner>(R.id.s_arrive_place)



        val arrayAdapter = ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item)
        s_travel_place.adapter=arrayAdapter
        s_arrive_place.adapter=arrayAdapter
        object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }.also { s_travel_place.onItemSelectedListener = it }


        //traveller
        val s_traveller_number =findViewById<Spinner>(R.id.s_traveller_number)
        val arrayAdapter_traveller = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,traveller)
        s_traveller_number.adapter=arrayAdapter_traveller
        object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }.also { s_travel_place.onItemSelectedListener = it }


        //degree

        val s_ticket_degree =findViewById<Spinner>(R.id.s_ticket_degree)
        val arrayAdapter_degree = ArrayAdapter.createFromResource(
            this,
            R.array.ticket_degree,
            android.R.layout.simple_spinner_item
        )
        s_ticket_degree.adapter=arrayAdapter_degree
        object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }.also { s_travel_place.onItemSelectedListener = it }


        //time

        val s_travel_time =findViewById<Spinner>(R.id.s_travel_time)
        val arrayAdapter_time = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,travel_time)
        s_travel_time.adapter=arrayAdapter_time
        object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }.also { s_travel_place.onItemSelectedListener = it }


        et_travel_date.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    et_travel_date.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }



    }

}