package mytrain.osmx.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserData(
    val admin: Boolean = false,
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
//    val tickets: Objects? = null
)
