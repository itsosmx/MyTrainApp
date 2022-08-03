package mytrain.bluestars.me.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import mytrain.bluestars.me.R
import mytrain.bluestars.me.auth.Login
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation

class AdminProfile : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var loading: LoadingDialog
    private lateinit var database: DatabaseReference
    private lateinit var tv_username: TextView
    private lateinit var btn_scan: Button
    private lateinit var btn_logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        supportActionBar?.hide()

        tv_username = findViewById(R.id.tv_username)
        loading = LoadingDialog(this)
        fAuth = FirebaseAuth.getInstance()
        btn_scan = findViewById(R.id.btn_scan)
        btn_logout = findViewById(R.id.btn_logout)

        tv_username.text = fAuth.currentUser?.email.toString()

        btn_scan.setOnClickListener {
            Navigation().Navigate(this@AdminProfile, ReadTicket::class.java)
        }

        btn_logout.setOnClickListener {
            loading.startLoading()
            fAuth.signOut()
            Navigation().Navigate(this@AdminProfile, Login::class.java)
            loading.endLoading()
        }
    }

    override fun onBackPressed() {
        return
    }
}