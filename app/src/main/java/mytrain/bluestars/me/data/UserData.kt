package mytrain.bluestars.me.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class UserData(
    val admin: Boolean = false,
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
//    val tickets: Objects? = null
)
