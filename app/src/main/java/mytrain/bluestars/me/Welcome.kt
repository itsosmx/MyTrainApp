package mytrain.bluestars.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.bluestars.me.admin.AdminProfile
import mytrain.bluestars.me.auth.Login
import mytrain.bluestars.me.auth.Signup
import mytrain.bluestars.me.components.DatabaseManager
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.data.UserData

class Welcome : AppCompatActivity() {
    private lateinit var btn_signup: Button
    private lateinit var btn_login: Button
    private lateinit var fAuth: FirebaseAuth
//    private lateinit var loading: LoadingDialog
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()
        btn_signup = findViewById(R.id.btn_signup)
        btn_login = findViewById(R.id.btn_login)
        fAuth = FirebaseAuth.getInstance()
//        loading = LoadingDialog(this)
        database = FirebaseDatabase.getInstance().getReference()

        btn_signup.setOnClickListener {
            Navigation().Navigate(this, Signup::class.java)
        }
        btn_login.setOnClickListener {
            Navigation().Navigate(this, Login::class.java)
        }
    }

    // Check if the user logged and send him to Home if true
    public override fun onStart() {
        super.onStart()
        if (fAuth.currentUser != null) {
//            loading.startLoading()
            database.child("users").child(fAuth.currentUser!!.uid).get()
                .addOnSuccessListener {
                   if (it.exists()) {
                       val user = it.getValue(UserData::class.java)
                       Log.i("User", user.toString())
                       if (user!!.admin) {
                           Navigation().Navigate(this@Welcome, AdminProfile::class.java)
                       } else Navigation().Navigate(this@Welcome, Home::class.java)
                   }
//                    loading.endLoading()
                }
                .addOnFailureListener {
                    Navigation().Navigate(this@Welcome, Home::class.java)
//                    loading.endLoading()
                }
        } else {
//            loading.endLoading()
            Navigation().Navigate(this@Welcome, Login::class.java)
        }

    }
}