package mytrain.bluestars.me.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R
import mytrain.bluestars.me.data.CitySpinnerData


class TicketInfo : AppCompatActivity() {
    private lateinit var tv_from: TextView
    private lateinit var tv_to: TextView
    private lateinit var b_next: Button
    private lateinit var tv_ticket_price: TextView
    private lateinit var b_cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_info)

        supportActionBar?.hide()

        b_next = findViewById(R.id.b_next)
        tv_from = findViewById(R.id.tv_from)
        tv_to = findViewById(R.id.tv_to)
        tv_ticket_price = findViewById(R.id.tv_ticket_price)
        b_cancel = findViewById(R.id.b_cancel)

        val startStation: CitySpinnerData =
            intent.getSerializableExtra("start_station") as CitySpinnerData
        val endStation: CitySpinnerData =
            intent.getSerializableExtra("end_station") as CitySpinnerData

        val departureDate = intent.getStringExtra("departure_date")
        val travelerNumber = intent.getStringExtra("traveler_number")
        val ticketClass = intent.getStringExtra("ticket_class")
        val travelTime = intent.getStringExtra("travel_time")
        val trainId = intent.getStringExtra("train_id")
        val arrivalTime = intent.getStringExtra("arrival_time")
        val departureTime = intent.getStringExtra("departure_time")
        val type = intent.getStringExtra("type")
        val totalPrice = intent.getStringExtra("price")

        tv_from.text = startStation.name
        tv_to.text = endStation.name
        tv_ticket_price.text = "$totalPrice\nEGP"

        b_next.setOnClickListener {
            val intent = Intent(this@TicketInfo, TicketPayment::class.java)
            intent.putExtra("start_station", startStation)
            intent.putExtra("end_station", endStation)
            intent.putExtra("departure_date", departureDate)
            intent.putExtra("traveler_number", travelerNumber)
            intent.putExtra("ticket_class", ticketClass)
            intent.putExtra("travel_time", travelTime)
            intent.putExtra("train_id", trainId)
            intent.putExtra("arrival_time", arrivalTime)
            intent.putExtra("departure_time", departureTime)
            intent.putExtra("type", type)
            intent.putExtra("price", totalPrice)
            startActivity(intent)
        }
        b_cancel.setOnClickListener {
            val intent = Intent(this@TicketInfo, Home::class.java)
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        return
    }

}

