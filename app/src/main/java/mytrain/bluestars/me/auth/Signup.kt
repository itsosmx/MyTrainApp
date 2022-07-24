package mytrain.bluestars.me.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.data.UserData
import java.lang.Exception

class Signup : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var formSubmit: Button
    private lateinit var formLogin: Button
    private lateinit var formName: EditText
    private lateinit var database: DatabaseReference

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
        database = FirebaseDatabase.getInstance().getReference()
        formName = findViewById(R.id.et_username_input)

        formLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        formSubmit.setOnClickListener {
            onSignup(formName.text.toString(), formEmail.text.toString(), formPassword.text.toString())
        }

    }

    private fun onSignup(name: String, email: String, password: String) {
        try {
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                        response ->
                    if (response.isSuccessful) {
                        val userId = response.result.user?.uid.toString()
                        val userDate = UserData(admin = false, uid = userId, displayName =  name, email = email)
                        database.child("users").child(userId).setValue(userDate)
                            .addOnCompleteListener {
                                Toast.makeText(this@Signup, "Account created!", Toast.LENGTH_SHORT).show()
                                Navigation().Navigate(this@Signup, Home::class.java)
                            }
                        val profileUpdate = userProfileChangeRequest {
                            displayName = name
                        }
                        fAuth.currentUser?.updateProfile(profileUpdate)
                    } else {
                        var message = ""
                        val errorMessage = response.exception.toString()
                        if (errorMessage.contains("password")) {
                            message = "Invalid password"
                        } else if (errorMessage.contains("email")) {
                            message = "Invalid email"
                        }
                        Toast.makeText(this@Signup, message, Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this@Signup, "Sorry, something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}