package mytrain.bluestars.me.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R
import java.lang.Exception

class Signup : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var formSubmit: Button
    private lateinit var formLogin: Button
    private lateinit var formName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Hide top bar
        supportActionBar?.hide()


        fAuth = FirebaseAuth.getInstance()
        formEmail = findViewById(R.id.et_email_input)
        formPassword = findViewById(R.id.et_password_input)
        formSubmit = findViewById(R.id.b_submit)
        formLogin = findViewById(R.id.b_to_login)


        formLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        formSubmit.setOnClickListener {
            onSignup( formEmail.text.toString(), formPassword.text.toString())
        }

    }

    private fun onSignup(email: String, password: String) {
        try {
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                        response ->
                    if (response.isSuccessful) {
                        val intent = Intent(this, Home::class.java)
                        finish()
                        startActivity(intent)
                        Toast.makeText(this@Signup, "Account created!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@Signup, "Sorry, something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this@Signup, "Sorry, something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}