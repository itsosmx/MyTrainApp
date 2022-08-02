package mytrain.bluestars.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.payment.BuyTicket

class Home: BaseActivity()  {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var btn_buy_ticket: Button
    private lateinit var btn_track: Button
    private lateinit var btn_inquiry: Button
    private lateinit var btn_news: Button
    private lateinit var btn_suggestions: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Home"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        fAuth = FirebaseAuth.getInstance()

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket)
        btn_track = findViewById(R.id.btn_track)
        btn_inquiry = findViewById(R.id.btn_inquiry)
        btn_news = findViewById(R.id.btn_news)
        btn_suggestions = findViewById(R.id.btn_suggestions)

        btn_buy_ticket.setOnClickListener {
            Navigation().Navigate(this@Home, BuyTicket::class.java)
        }
        btn_track.setOnClickListener {
            Navigation().Navigate(this@Home, Map::class.java)
        }
        btn_inquiry.setOnClickListener {
            Navigation().Navigate(this@Home, Enquiry::class.java)
        }
        btn_news.setOnClickListener {
            Navigation().Navigate(this@Home, News::class.java)
        }
        btn_suggestions.setOnClickListener {
            Navigation().Navigate(this@Home, Suggestions::class.java)
        }
    }
}