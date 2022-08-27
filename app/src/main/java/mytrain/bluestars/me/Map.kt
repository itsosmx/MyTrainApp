package mytrain.bluestars.me

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import mytrain.bluestars.me.data.LocationData
import mytrain.bluestars.me.data.StationData


class Map : AppCompatActivity(), OnMapReadyCallback {

    //    lateinit var dbref: DatabaseReference
//    var currentLocation : Location? = null
//    var fusedLocationProviderClient: FusedLocationProviderClient? = null
//    val REQUEST_CODE = 101
    private lateinit var mMap:GoogleMap
    public lateinit var databaseRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.title = "تتبع قطاري"
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync(this)
        databaseRef = Firebase.database.reference
        databaseRef.addValueEventListener(logListener)


    }


    val logListener =object :ValueEventListener {


        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {

                val location = snapshot.child("GPS").getValue(LocationData::class.java)
                var lat = location?.f_latitude
                var long = location?.f_longitude

                if (lat != null && long != null) {

                    val latLng = LatLng(lat, long)
                    val markerOptions = MarkerOptions().position(latLng).title("I Am Here!")
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
                    mMap.addMarker(markerOptions)
                    Toast.makeText(
                        applicationContext,
                        "Locations accessed for the database",
                        Toast.LENGTH_LONG
                    ).show()


                }
            }

        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(
                applicationContext,
                "Could not read from database",

                Toast.LENGTH_LONG
            ).show()

        }


    }

    override fun onMapReady(googleMap: GoogleMap){
        mMap = googleMap

       // val sydney = LatLng(-34.0,151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker"))
        //.mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
