package mytrain.osmx.me

import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import mytrain.osmx.me.components.Navigation
import mytrain.osmx.me.payment.MyTicketsList
import mytrain.osmx.me.payment.TicketPicker

class Home : BaseActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var btn_buy_ticket: Button
    private lateinit var btn_track: Button
    private lateinit var btn_myTickets: Button
    private lateinit var btn_news: Button
    private lateinit var btn_suggestions: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "الرئيسية"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        fAuth = FirebaseAuth.getInstance()

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket)
        btn_track = findViewById(R.id.btn_track)
        btn_myTickets = findViewById(R.id.btn_myTickets)
        btn_news = findViewById(R.id.btn_news)
        btn_suggestions = findViewById(R.id.btn_suggestions)

        btn_buy_ticket.setOnClickListener {
            Navigation().Navigate(this@Home, TicketPicker::class.java)
        }
        btn_track.setOnClickListener {
        Navigation().Message(this, "غير متاح حاليا")
        //Navigation().Navigate(this@Home, Map::class.java)
        }
        btn_myTickets.setOnClickListener {
            Navigation().Navigate(this@Home, MyTicketsList::class.java)
        }
        btn_news.setOnClickListener {
            Navigation().Navigate(this@Home, News::class.java)
        }
        btn_suggestions.setOnClickListener {
            Navigation().Navigate(this@Home, Suggestions::class.java)
        }
    }

    override fun onBackPressed() {
        return
    }
}