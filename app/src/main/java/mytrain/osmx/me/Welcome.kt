package mytrain.osmx.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.osmx.me.auth.Login
import mytrain.osmx.me.auth.Signup
import mytrain.osmx.me.components.Navigation

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
        database = FirebaseDatabase.getInstance().getReference("")

        btn_signup.setOnClickListener {
            Navigation().Navigate(this@Welcome, Signup::class.java)
        }
        btn_login.setOnClickListener {
            Navigation().Navigate(this@Welcome, Login::class.java)
        }
    }

    // Check if the user logged and send him to Home if true
    public override fun onStart() {
        super.onStart()
        fAuth.addAuthStateListener {
            if (it.currentUser != null) {
                Navigation().Navigate(this@Welcome, Home::class.java)
            }
        }
    }
}