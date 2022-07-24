package mytrain.bluestars.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import mytrain.bluestars.me.payment.TicketInfo

class Enquiry : BaseActivity() {
    val traveller = arrayOf("اختر عدد المسافرين",1,2,3,4)
    val travel_time = arrayOf("اختر توقيت السفر ","صباحاً","مساءاً")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquiry)


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





    }

}