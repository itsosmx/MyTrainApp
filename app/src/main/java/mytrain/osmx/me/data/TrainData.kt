package mytrain.osmx.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TrainData(
    val latitude: String? = "0",
    val longitude: String? = "0",
    val tickets: Array<String>? = null
)
