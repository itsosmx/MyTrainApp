package mytrain.bluestars.me.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import mytrain.bluestars.me.R


class Ticket : AppCompatActivity() {
    private lateinit var tv_s_station: TextView
    private lateinit var tv_e_station: TextView
    private lateinit var btn_print: Button
    private lateinit var tv_train_degree: TextView
    private lateinit var tv_time_date: TextView
    var im: ImageView? = null
    //val preferences = getSharedPreferences("MyLogin", MODE_PRIVATE)

    private lateinit var myName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        supportActionBar?.hide()

        btn_print = findViewById(R.id.btn_print)
        tv_s_station = findViewById(R.id.tv_s_station)
        tv_e_station = findViewById(R.id.tv_e_station)
        tv_train_degree = findViewById(R.id.tv_train_degree)
        tv_time_date = findViewById(R.id.tv_time_date)
        myName = findViewById((R.id.tv_egp))
        im = findViewById(R.id.im_view)


        val to = intent.getStringExtra("to")
        val from = intent.getStringExtra("from")

        val traveler_number = intent.getStringExtra("traveler_number")
        val ticket_degree = intent.getStringExtra("ticket_degree")
        val travel_time = intent.getStringExtra("date")

        tv_s_station.text = from
        tv_e_station.text = to
        tv_time_date.text = travel_time
        tv_train_degree.text = ticket_degree

        btn_print.setOnClickListener {
            val intent = Intent(this@Ticket, Ticket::class.java)
            intent.putExtra("from", from)
            intent.putExtra("to", to)
            intent.putExtra("traveler_number", traveler_number)
            intent.putExtra("ticket_degree", ticket_degree)
            intent.putExtra("travel_time", travel_time)
            startActivity(intent)
        }
        generateQrCode("Ticket ID")
        //generateQrCode(preferences.getString("userID", ""))
        //Log.i("qr", preferences.getString("userID", "").toString())
    }
    fun generateQrCode(text: String?){
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 500)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            im?.setImageBitmap(bMap)
        } catch (e: Exception){
            Log.e("Generate QR ", e.toString())
        }
    }
}