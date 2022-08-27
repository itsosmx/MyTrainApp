package mytrain.bluestars.me.payment

import android.content.Intent
import android.os.Bundle
import android.widget.*
import mytrain.bluestars.me.R
import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import androidx.core.view.isEmpty
import mytrain.bluestars.me.BaseActivity
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.data.CitySpinnerData
import java.util.*


class TicketPicker : BaseActivity() {
    val traveller = arrayOf(1, 2, 3, 4)
    val travel_time = arrayOf("صباحاً", "مساءاً")
    private lateinit var b_next: Button
    lateinit var et_travel_date: EditText
    private lateinit var city_adapter: ArrayAdapter<CitySpinnerData>
    private lateinit var SelectedEndStation: CitySpinnerData
    private lateinit var SelectedStartStation: CitySpinnerData
    private lateinit var s_end_station: Spinner
    private lateinit var s_start_station: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_picker)
        //remove topbar
        supportActionBar?.hide()

        et_travel_date = findViewById(R.id.et_travel_date)
        b_next = findViewById(R.id.b_next)

        s_end_station = findViewById(R.id.s_end_station)
        s_start_station = findViewById(R.id.s_start_station)

        val customObjects = CitySpinnerData().applyDefaultCities();
        city_adapter = ArrayAdapter(this, R.layout.spinner_item, customObjects)
        city_adapter.setDropDownViewResource(R.layout.spinner_item);

        s_end_station.adapter = city_adapter
        s_start_station.adapter = city_adapter

        s_end_station.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SelectedEndStation = s_end_station.selectedItem as CitySpinnerData
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        s_start_station.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SelectedStartStation = s_start_station.selectedItem as CitySpinnerData
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }


        //traveller
        val s_traveller_number = findViewById<Spinner>(R.id.s_traveller_number)
        val arrayAdapter_traveller = ArrayAdapter(this, R.layout.spinner_item, traveller)
        arrayAdapter_traveller.setDropDownViewResource(R.layout.spinner_item);

        s_traveller_number.adapter = arrayAdapter_traveller

        //degree

        val s_ticket_degree = findViewById<Spinner>(R.id.s_ticket_degree)
        val arrayAdapter_degree = ArrayAdapter.createFromResource(
            this,
            R.array.ticket_degree,
            android.R.layout.simple_spinner_item
        )
        s_ticket_degree.adapter = arrayAdapter_degree

        //time

        val s_travel_time = findViewById<Spinner>(R.id.s_travel_time)
        val arrayAdapter_time =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, travel_time)
        s_travel_time.adapter = arrayAdapter_time
        b_next.setOnClickListener {
            val intent = Intent(this@TicketPicker, TicketsList::class.java)

            intent.putExtra("start_station", SelectedStartStation)
            intent.putExtra("end_station", SelectedEndStation)
            intent.putExtra("date", et_travel_date.text.toString())
            intent.putExtra("traveler_number", s_traveller_number.selectedItem.toString())
            intent.putExtra("ticket_class", s_ticket_degree.selectedItem.toString())
            intent.putExtra("travel_time", s_travel_time.selectedItem.toString())

            if (et_travel_date.text.isEmpty() || s_traveller_number.isEmpty() || s_travel_time.isEmpty() || s_ticket_degree.isEmpty()) {
                Navigation().Message(this, "ارجو ملئ كل البيانات")
            } else if (SelectedStartStation.id == SelectedEndStation.id) {
                Navigation().Message(this, "تاكد من صحة البيانات المدرجة")
            }
            else startActivity(intent)
        }


        et_travel_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    et_travel_date.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

    }
}