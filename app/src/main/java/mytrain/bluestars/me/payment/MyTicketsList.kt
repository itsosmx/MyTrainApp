

package mytrain.bluestars.me.payment
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import mytrain.bluestars.me.BaseActivity
import mytrain.bluestars.me.R
import mytrain.bluestars.me.data.TicketData


class MyTicketsList : BaseActivity() {
    lateinit var dbref: DatabaseReference
    lateinit var stationRecyclereview: RecyclerView
    lateinit var stationArrayList: ArrayList<TicketData>
    lateinit var adapter: MyTicketAdapter
    lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tickets_list)

        //remove topbar
        supportActionBar?.hide()

//        val to = intent.getStringExtra("to")
//        val from = intent.getStringExtra("from")
        fAuth = FirebaseAuth.getInstance()

        stationRecyclereview = findViewById(R.id.stationList)
        stationRecyclereview.layoutManager = LinearLayoutManager(this)
        stationArrayList = ArrayList()


        adapter = MyTicketAdapter(stationArrayList, intent)
        stationRecyclereview.adapter = adapter
//        getTicketsList(from.toString() , to.toString())
        dbref = FirebaseDatabase.getInstance().getReference("")


        dbref.child("users")
            .child(fAuth.currentUser!!.uid)
            .child("tickets")


            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val station = snap.getValue(TicketData::class.java)
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