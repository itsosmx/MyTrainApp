package mytrain.bluestars.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.data.SuggestionsData

class Suggestions : BaseActivity() {
    private lateinit var b_submit: Button
    private lateinit var et_content_input: EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)

        b_submit = findViewById(R.id.b_submit)
        et_content_input = findViewById(R.id.et_content_input)
        fAuth = FirebaseAuth.getInstance()
        loading = LoadingDialog(this)

        dbRef = FirebaseDatabase.getInstance().getReference()
        b_submit.setOnClickListener {
            createSuggestion()
        }
    }

    private fun createSuggestion() {
        loading.startLoading()
        val content = et_content_input.text.toString()
        val userId = fAuth.currentUser?.uid
        if (content.isEmpty()) {
            return Navigation().Message(this, "please enter your suggestions")
        }
        val snapshot = SuggestionsData(content , userId)
        if (userId != null) {
            dbRef.child("suggestions").child(userId).push()
                .setValue(snapshot)
                .addOnSuccessListener {
                    Navigation().Message(this, "Your message has been sent successfully")
                    et_content_input.text.clear()
                }.addOnFailureListener{
                    Navigation().Message(this, "Sorry, something went wrong.")
                }
        }
        loading.endLoading()
    }



}