package mytrain.osmx.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.osmx.me.admin.AdminProfile
import mytrain.osmx.me.auth.Login
import mytrain.osmx.me.components.Navigation
import mytrain.osmx.me.data.UserData

class UserCheck : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_check)
        supportActionBar?.hide()
        fAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("")
    }

    public override fun onStart() {
        super.onStart()
        if (fAuth.currentUser != null) {
            database.child("users").child(fAuth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val user = it.getValue(UserData::class.java)
                        Log.i("User", user.toString())
                        if (user!!.admin) Navigation().Navigate(this@UserCheck, AdminProfile::class.java)
                        else Navigation().Navigate(this@UserCheck, Home::class.java)
                    }
                }
                .addOnFailureListener {
                    Navigation().Navigate(this@UserCheck, Home::class.java)
                }
        } else {
            Navigation().Navigate(this@UserCheck, Login::class.java)
        }
    }
}