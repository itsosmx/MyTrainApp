package mytrain.bluestars.me.payment
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mytrain.bluestars.me.BaseActivity
import mytrain.bluestars.me.adapters.StationAdapter
import mytrain.bluestars.me.data.StationData
import mytrain.bluestars.me.R


class TicketsList : BaseActivity() {
    lateinit var dbref: DatabaseReference
    lateinit var stationRecyclereview: RecyclerView
    lateinit var stationArrayList: ArrayList<StationData>
    lateinit var adapter: StationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_list)

        //remove topbar
        supportActionBar?.hide()

        val to = intent.getStringExtra("to")
        val from = intent.getStringExtra("from")

        stationRecyclereview = findViewById(R.id.stationList)
        stationRecyclereview.layoutManager = LinearLayoutManager(this)
        stationArrayList = ArrayList()


        adapter = StationAdapter(stationArrayList )
        stationRecyclereview.adapter =adapter
        getTicketsList(from.toString() , to.toString())

    }
    private fun getTicketsList(from: String, to: String) {
        dbref = FirebaseDatabase.getInstance().getReference("")
        dbref.child("stations")
            .child(from)
            .child(to)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val station = snap.getValue(StationData::class.java)
                            stationArrayList.add(station!!)
                        }
                        adapter.notifyDataSetChanged()
                    }

                    var adapter = StationAdapter(stationArrayList)
                    stationRecyclereview.adapter = adapter
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}