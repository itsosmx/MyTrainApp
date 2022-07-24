package mytrain.bluestars.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.data.SuggestionsData

class Suggestions : BaseActivity() {
    private lateinit var submit: Button
    private lateinit var etTypeHere: EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)

        submit = findViewById(R.id.submit)
        etTypeHere = findViewById(R.id.etTypeHere)
        fAuth = FirebaseAuth.getInstance()
        loading = LoadingDialog(this)

        dbRef = FirebaseDatabase.getInstance().getReference()
        submit.setOnClickListener {
            saveEmployeeData()

        }
    }

    private fun saveEmployeeData() {
        //getting values
        loading.startLoading()
        val TypeHere = etTypeHere.text.toString()
        if (TypeHere.isEmpty()) {
            etTypeHere.error = "please enter your suggestions"
        }
        val userId= fAuth.currentUser?.uid
        val snapshot = SuggestionsData(TypeHere , userId)
        if (userId != null) {
            dbRef.child("suggestions").child(userId).setValue(snapshot)
                .addOnCompleteListener{
                    Toast.makeText( this,"Data inserted successfully",Toast.LENGTH_LONG).show()
                    etTypeHere.text.clear()
                }.addOnFailureListener{err ->
                    Toast.makeText( this,"Error ${err.message}",Toast.LENGTH_LONG).show()
                }
        }
        loading.endLoading()
    }



}