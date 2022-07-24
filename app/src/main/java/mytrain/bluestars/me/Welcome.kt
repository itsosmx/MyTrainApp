package mytrain.bluestars.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import mytrain.bluestars.me.auth.Login
import mytrain.bluestars.me.auth.Signup
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation

class Welcome : AppCompatActivity() {
    private lateinit var btn_signup: Button
    private lateinit var btn_login: Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()



        btn_signup = findViewById(R.id.btn_signup)
        btn_login = findViewById(R.id.btn_login)
        fAuth = FirebaseAuth.getInstance()
        loading = LoadingDialog(this)


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
        loading.startLoading()
        FirebaseAuth.AuthStateListener {
            user ->
                if (user != null) {
                    Navigation().Navigate(this@Welcome, Home::class.java)
                } else {
                    Navigation().Navigate(this@Welcome, Login::class.java)
                }
        }
        loading.endLoading()
    }
}