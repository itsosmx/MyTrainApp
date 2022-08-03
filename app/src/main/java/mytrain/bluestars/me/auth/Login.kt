package mytrain.bluestars.me.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R
import mytrain.bluestars.me.UserCheck
import mytrain.bluestars.me.admin.AdminProfile
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation


class Login : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var formSubmit: Button
    private lateinit var formSignUp: Button
//    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Hide top bar
        supportActionBar?.hide()

        fAuth = FirebaseAuth.getInstance()
        formEmail = findViewById(R.id.et_email_input)
        formPassword = findViewById(R.id.et_password_input)
        formSubmit = findViewById(R.id.b_submit)
        formSignUp = findViewById(R.id.b_to_signup)
//        loading = LoadingDialog(this)



        //Go to Sign Up screen on click
        formSignUp.setOnClickListener {
            Navigation().Navigate(this, Signup::class.java)
        }

        formSubmit.setOnClickListener {
            onLogin("local", formEmail.text.toString(), formPassword.text.toString())
        }
    }

    private fun onLogin(type: String, email: String, password: String) {
        try {
            if (type == "local") {
                fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        response ->
                        if (response.isSuccessful) {
//                            loading.endLoading()
                            Navigation().Navigate(this@Login, UserCheck::class.java)
                        } else {
//                            loading.endLoading()
                            Toast.makeText(this@Login, "Sorry, something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        } catch (e: Exception) {
//            loading.endLoading()
            Toast.makeText(this@Login, "Sorry, something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}