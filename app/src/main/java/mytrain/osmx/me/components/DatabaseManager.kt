package mytrain.osmx.me.components

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mytrain.osmx.me.data.UserData

class DatabaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    fun getUser(): UserData? {
        var user : UserData? = null
        database.child("users").child(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    user = it.getValue(UserData::class.java)
                }
            }
        return user
    }
}