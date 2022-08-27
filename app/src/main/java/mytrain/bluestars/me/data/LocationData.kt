package mytrain.bluestars.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationData (

    var f_latitude:Double? = null,
    var f_longitude:Double? = null

)