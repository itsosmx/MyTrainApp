package mytrain.osmx.me.payment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mytrain.osmx.me.BaseActivity
import mytrain.osmx.me.adapters.StationAdapter
import mytrain.osmx.me.data.StationData
import mytrain.osmx.me.data.CitySpinnerData
import mytrain.osmx.me.R

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

        val startStation: CitySpinnerData? =
            intent.getSerializableExtra("start_station") as CitySpinnerData?
        val endStation: CitySpinnerData? =
            intent.getSerializableExtra("end_station") as CitySpinnerData?

        stationRecyclereview = findViewById(R.id.stationList)
        stationRecyclereview.layoutManager = LinearLayoutManager(this)
        stationArrayList = ArrayList()

        println("//////////////////////")
        println(startStation!!.id)
        println(endStation!!.id)
        println("//////////////////////")

        adapter = StationAdapter(stationArrayList, intent)
        stationRecyclereview.adapter = adapter
        getTicketsList(startStation!!.id, endStation!!.id)

    }

    private fun getTicketsList(from: String, to: String) {
        dbref = FirebaseDatabase.getInstance().getReference("")
        dbref.child("stations")
            .child(from)
            .child(to)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val station = snap.getValue(StationData::class.java)
                            stationArrayList.add(station!!)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}