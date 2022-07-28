package mytrain.bluestars.me

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import mytrain.bluestars.me.adapters.StationAdapter
import mytrain.bluestars.me.data.StationData


private lateinit var dbref: DatabaseReference
private lateinit var stationRecyclereview: RecyclerView
private lateinit var stationArrayList: ArrayList<StationData>
private lateinit var adapter: StationAdapter

class TicketsList : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_list)
        stationRecyclereview = findViewById(R.id.stationList)
        stationRecyclereview.layoutManager = LinearLayoutManager(this)
        stationArrayList = ArrayList()


         adapter = StationAdapter(stationArrayList )
        stationRecyclereview.adapter =adapter
        getUserData()
    }
    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("")
        dbref.child("stations")
            .child("benisuef")
            .child("cairo")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val station = snap.getValue(StationData::class.java)
                            stationArrayList.add(station!!)
                        }
                        adapter.notifyDataSetChanged()
                    }}

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}