package mytrain.bluestars.me.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import mytrain.bluestars.me.R
import android.app.DatePickerDialog
import android.widget.EditText
import mytrain.bluestars.me.BaseActivity
import java.util.*


class TicketPicker : BaseActivity() {
    val traveller = arrayOf("اختر عدد المسافرين",1,2,3,4)
    val travel_time = arrayOf("اختر توقيت السفر ","صباحاً","مساءاً")
    private lateinit var b_next: Button
    lateinit var et_travel_date: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket)

        //remove topbar
        supportActionBar?.hide()


        et_travel_date = findViewById(R.id.et_travel_date)
        b_next = findViewById(R.id.b_next)

        val s_travel_place =findViewById<Spinner>(R.id.s_travel_place)
        val s_arrive_place =findViewById<Spinner>(R.id.s_arrive_place)
        //val et_travel_date =findViewById<DatePicker>(R.id.et_travel_date)

        val arrayAdapter = ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item)
        s_travel_place.adapter=arrayAdapter
        s_arrive_place.adapter=arrayAdapter

        //traveller
        val s_traveller_number =findViewById<Spinner>(R.id.s_traveller_number)
        val arrayAdapter_traveller = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,traveller)
        s_traveller_number.adapter =arrayAdapter_traveller

        //degree

        val s_ticket_degree =findViewById<Spinner>(R.id.s_ticket_degree)
        val arrayAdapter_degree = ArrayAdapter.createFromResource(
            this,
            R.array.ticket_degree,
            android.R.layout.simple_spinner_item
        )
        s_ticket_degree.adapter=arrayAdapter_degree

        //time

        val s_travel_time =findViewById<Spinner>(R.id.s_travel_time)
        val arrayAdapter_time = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,travel_time)
        s_travel_time.adapter=arrayAdapter_time


        b_next.setOnClickListener {
            val intent = Intent(this@TicketPicker, TicketsList::class.java)
            intent.putExtra("from", s_travel_place.selectedItem.toString())
            intent.putExtra("to", s_arrive_place.selectedItem.toString())
            intent.putExtra("date", et_travel_date.text.toString())
            intent.putExtra("traveler_number", s_traveller_number.selectedItem.toString())
            intent.putExtra("ticket_class",s_ticket_degree.selectedItem.toString())
            intent.putExtra("travel_time",s_travel_time.selectedItem.toString())
            startActivity(intent)
        }


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