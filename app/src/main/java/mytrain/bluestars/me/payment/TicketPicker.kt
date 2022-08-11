package mytrain.bluestars.me.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import mytrain.bluestars.me.R
import android.app.DatePickerDialog
import android.graphics.Insets.add
import android.widget.EditText
import mytrain.bluestars.me.BaseActivity
import mytrain.bluestars.me.data.cityes
import java.util.*


class TicketPicker : BaseActivity() {
    val traveller = arrayOf("اختر عدد المسافرين",1,2,3,4)
    val travel_time = arrayOf("اختر توقيت السفر ","صباحاً","مساءاً")
    private lateinit var b_next: Button
    lateinit var et_travel_date: EditText
    private lateinit var mySpinner: Spinner
    private lateinit var adapter: ArrayAdapter<cityes>


    private fun getCustomObjects(): ArrayList<cityes> {
        val customObjects = ArrayList<cityes>()
        customObjects.apply {
            add(cityes("", "حدد المحطة "))
            add(cityes("bani_suef", "بنى سويف"))
            add(cityes("cairo", "القاهره"))
            add(cityes("giza", "الجيزه"))
            add(cityes("asyut", "اسيوط"))
            add(cityes("alexandria", "الأسكندرية"))
            add(cityes("ismailia", "لأسماعيلية"))
            add(cityes("fayoum", "الفيوم"))
            add(cityes("luxor", "الأقصر"))
            add(cityes("zagazig", "الزقازيق"))
            add(cityes("suez", "السويس"))
            add(cityes("aswan", "اسوان"))
            add(cityes("mahalla_al_kubra", "المحله الكبرى"))
            add(cityes("mansoura", "المنصورة"))
            add(cityes("minya", "المنيا"))
            add(cityes("banha", "بنها"))
            add(cityes("port_said", "بورسعيد"))
            add(cityes("damanhour", "دمنهور"))
            add(cityes("domiette", "دمياط"))
            add(cityes("sohage", "سوهاج"))
            add(cityes("sidi gaber", "سيدى جابر"))
            add(cityes("shubra_el_kheima", "شبرا الخيمه"))
            add(cityes("tanta", "طنطا"))
            add(cityes("qena", "قنا"))
            add(cityes("kafr_al_sheikh", "كفر الشيخ"))
            add(cityes("marsa_matrouh", "مرسى مطروح"))
            add(cityes("itai_el_baroud", "ايتاى البارود"))

        }
        return customObjects
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket)

        //remove topbar
        supportActionBar?.hide()


        et_travel_date = findViewById(R.id.et_travel_date)
        b_next = findViewById(R.id.b_next)

        val s_travel_place =findViewById<Spinner>(R.id.s_travel_place)
        val s_arrive_place =findViewById<Spinner>(R.id.s_arrive_place)

/*
        //travel & arrive
        val arrayAdapter = ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item)
        s_travel_place.adapter=arrayAdapter
        s_arrive_place.adapter=arrayAdapter
*/
        val customObjects = getCustomObjects()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, customObjects)
        s_travel_place.adapter = adapter
        s_arrive_place.adapter = adapter

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