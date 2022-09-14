package mytrain.osmx.me.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import mytrain.osmx.me.Home
import mytrain.osmx.me.R
import mytrain.osmx.me.components.Navigation
import mytrain.osmx.me.data.TicketData
import java.util.*


class Ticket : AppCompatActivity() {
    private lateinit var tv_s_station: TextView
    private lateinit var tv_e_station: TextView
    private lateinit var btn_delete1: Button
    private lateinit var tv_train_degree: TextView
    private lateinit var tv_time_date: TextView
    private lateinit var ticketData: TicketData
    private lateinit var fAuth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var tv_departureTime: TextView
    private lateinit var tv_id: TextView
    private lateinit var tv_seatClass: TextView
    private lateinit var tv_car: TextView
    private lateinit var tv_egp: TextView
    private lateinit var myName: TextView
    var QRimage: ImageView? = null
    //val preferences = getSharedPreferences("MyLogin", MODE_PRIVATE)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        supportActionBar?.hide()
        fAuth = FirebaseAuth.getInstance()

        dbref = FirebaseDatabase.getInstance().getReference("")

        btn_delete1 = findViewById(R.id.btn_delete1)
        tv_s_station = findViewById(R.id.tv_s_station)
        tv_e_station = findViewById(R.id.tv_e_station)
        tv_train_degree = findViewById(R.id.tv_train_degree)
        tv_time_date = findViewById(R.id.tv_time_date)
        tv_departureTime = findViewById(R.id.tv_departureTime)
        tv_id = findViewById(R.id.tv_id)
        tv_seatClass = findViewById(R.id.tv_seatClass)
        tv_car = findViewById(R.id.tv_car)
        tv_egp = findViewById(R.id.tv_egp)

        myName = findViewById((R.id.tv_egp))
        QRimage = findViewById(R.id.im_view)
        ticketData = intent.getSerializableExtra("ticket") as TicketData


        tv_s_station.text = ticketData.startStation
        tv_e_station.text = ticketData.endStation
        tv_departureTime.text = ticketData.departureTime
        tv_train_degree.text = ticketData.seatClass
        tv_id.text = ticketData.trainId
        tv_time_date.text = ticketData.departureDate
        tv_seatClass.text = ticketData.seats.toString()
        tv_car.text = ticketData.status
        tv_egp.text = ticketData.amount


        //val newDate = Date(ticketData.validity - 172800000L) // 2 * 24 * 60 * 60 * 1000


        val time = System.currentTimeMillis()

        val days = (time - ticketData.validity!!)

        println("/////////////////////////////////////////")
        println(days)

        btn_delete1.setOnClickListener {
            delete()
        }


        generateQrCode("\n  محطة القيام  \n " +ticketData.startStation+ "\n  محطة الوصول  \n " +ticketData.endStation+"  \nتاريخ القيام   \n\n "+ticketData.departureDate+"  \n \n وقت القيام   \n\n "+ticketData.departureTime+"\n\n Ticket ID \n "+ticketData.id)

    }


//1661288715799
//1661289571184


    private fun delete() {

        val time = System.currentTimeMillis()

        val days = (time - ticketData.validity!!)




        dbref.child("users")
            .child(fAuth.currentUser!!.uid)
            .child("tickets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val ticket = snap.getValue(TicketData::class.java)
                            if (ticket?.id == ticketData.id) {
                                if (days < 0) {
                                    snap.ref.removeValue()
                                        .addOnSuccessListener {
                                            Navigation().Navigate(this@Ticket, Home::class.java)
                                            Navigation().Message(this@Ticket, "تمت العملية بنجاح")
                                        }

                                } else {
                                    Navigation().Message(
                                        this@Ticket,
                                        "لا يمكن الغاء الحجز الا قبل 24 ساعة من موعد الحجز"
                                    )
                                }

                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun generateQrCode(text: String?) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 500)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            QRimage?.setImageBitmap(bMap)
        } catch (e: Exception) {
            Log.e("Generate QR ", e.toString())
        }
    }
}