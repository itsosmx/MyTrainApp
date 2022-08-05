package mytrain.bluestars.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StationData (
    var id:String?=null,
    var arrivalTime:String?=null,
    var departureTime:String?=null,
    var price:Double?=null,
    var type:String?=null,
    val isArrivalSameDate: Boolean = true,
    val seatCLass: String? = null
)