package mytrain.bluestars.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TrainData(
    val latitude: String? = null,
    val longitude: String? = null,
)
