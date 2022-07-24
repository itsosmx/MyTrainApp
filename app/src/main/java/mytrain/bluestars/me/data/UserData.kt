package mytrain.bluestars.me.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserData(
    val admin: Boolean = false,
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
)
