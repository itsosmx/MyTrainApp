package mytrain.osmx.me.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.osmx.me.Home
import mytrain.osmx.me.R
import mytrain.osmx.me.components.Navigation
import mytrain.osmx.me.data.CitySpinnerData
import mytrain.osmx.me.data.TicketUnavailableSeats


class TicketInfo : AppCompatActivity() {
    private lateinit var tv_from: TextView
    private lateinit var tv_to: TextView
    private lateinit var b_next: Button
    private lateinit var tv_ticket_price: TextView
    private lateinit var b_cancel: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_info)

        supportActionBar?.hide()

        b_next = findViewById(R.id.b_next)
        tv_from = findViewById(R.id.tv_from)
        tv_to = findViewById(R.id.tv_to)
        tv_ticket_price = findViewById(R.id.tv_ticket_price)
        b_cancel = findViewById(R.id.b_cancel)
        database = FirebaseDatabase.getInstance().reference;

        val startStation: CitySpinnerData =
            intent.getSerializableExtra("start_station") as CitySpinnerData
        val endStation: CitySpinnerData =
            intent.getSerializableExtra("end_station") as CitySpinnerData

        val departureDate = intent.getStringExtra("departure_date")
        val travelerNumber = intent.getStringExtra("traveler_number")
        val ticketClass = intent.getStringExtra("ticket_class")
        val startTime = intent.getStringExtra("start_time")
        val trainId = intent.getStringExtra("train_id")
        val arrivalTime = intent.getStringExtra("arrival_time")
        val departureTime = intent.getStringExtra("departure_time")
        val type = intent.getStringExtra("type")
        val totalPrice = intent.getStringExtra("price")

        tv_from.text = startStation.name
        tv_to.text = endStation.name
        tv_ticket_price.text = "${totalPrice!!.toDouble() / 100}\nEGP"

        b_next.setOnClickListener {
            val intent = Intent(this@TicketInfo, TicketPayment::class.java)
            intent.putExtra("start_station", startStation)
            intent.putExtra("end_station", endStation)
            intent.putExtra("departure_date", departureDate)
            intent.putExtra("traveler_number", travelerNumber)
            intent.putExtra("ticket_class", ticketClass)
            intent.putExtra("start_time", startTime)
            intent.putExtra("train_id", trainId)
            intent.putExtra("arrival_time", arrivalTime)
            intent.putExtra("departure_time", departureTime)
            intent.putExtra("type", type)
            intent.putExtra("price", totalPrice)
            checkUnavailableSeats(trainId.toString(), intent)
        }
        b_cancel.setOnClickListener {
            val intent = Intent(this@TicketInfo, Home::class.java)
            startActivity(intent)
        }
    }

    fun checkUnavailableSeats(id : String, intent: Intent) {
        database.child("trains").child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val currentSeats = it.getValue(TicketUnavailableSeats::class.java);
                if (currentSeats != null) {
                    if (currentSeats.seats!! > currentSeats.unavailable!!) {
                        startActivity(intent)
                    } else {
                        Navigation().Message(this@TicketInfo, "هذا القطار لم يعد متاح.")
                    }
                }
            } else startActivity(intent)
        }
    }


    override fun onBackPressed() {
        return
    }

}

