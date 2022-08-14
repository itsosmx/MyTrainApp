package mytrain.bluestars.me

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import mytrain.bluestars.me.databinding.ActivityUpdateDataBinding

class UpdateData : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDataBinding
    private lateinit var database : DatabaseReference
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        fAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {

            val displayName = binding.displayName.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()


            updateData(displayName,email,password)

        }

    }

    private fun updateData(displayName: String,email: String,password:String) {

        database = FirebaseDatabase.getInstance().getReference("users")
        val user = mapOf<String,String>(
            "displayName" to displayName,
            "email" to email,



            )

        database.child(fAuth.currentUser!!.uid).child("email").setValue("user@example.com")
        database.child(fAuth.currentUser!!.uid).child("password").setValue("")
        database.child(fAuth.currentUser!!.uid).updateChildren(user).addOnSuccessListener {

            binding.displayName.text.clear()
            binding.email.text.clear()
            binding.password.text.clear()


            val user = Firebase.auth.currentUser

            user!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User email address updated.")
                    }
                    val user = Firebase.auth.currentUser

                    user!!.updatePassword(password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User password updated.")
                            }
                        }
                }

            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()

        }}
}