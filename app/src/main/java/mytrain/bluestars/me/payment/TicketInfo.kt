package mytrain.bluestars.me.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import mytrain.bluestars.me.R


class TicketInfo : AppCompatActivity() {
    private lateinit var tv_from: TextView
    private lateinit var tv_to:TextView
    private lateinit var b_next: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_info)
//
        b_next = findViewById(R.id.b_next)
        tv_from = findViewById(R.id.tv_from)
        tv_to = findViewById(R.id.tv_to)

        val to = intent.getStringExtra("to")
        val from = intent.getStringExtra("from")
        //
        val traveler_number =intent.getStringExtra("traveler_number")
        val ticket_degree =intent.getStringExtra("ticket_degree")
        val travel_time =intent.getStringExtra("travel_time")


        tv_from.text = from
        tv_to.text = to



        b_next.setOnClickListener {
            val intent = Intent(this@TicketInfo, PaymentMethod::class.java)
            intent.putExtra("from", from)
            intent.putExtra("to", to)
            //intent.putExtra("date",et_travel_date.)
            intent.putExtra("traveler_number", traveler_number)
            intent.putExtra("ticket_degree",ticket_degree)
            intent.putExtra("travel_time",travel_time)
            startActivity(intent)


        }



    }
}