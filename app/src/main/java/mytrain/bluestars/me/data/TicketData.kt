package mytrain.bluestars.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TicketData(
    val id: String? = null,
    val startStation: String? = null,
    val endStation: String? = null,
    val arrivalTime: String? = null,
    val departureTime: String? = null,
    val validity: String? = null,
    val trainId: String? = null,
    val seatClass: String? = null,
    val carNumber: String? = null,
    val seats: Int? = null,
    val price: String? = null,
    val departureDate: String? = null,
    val trainType: String? = null,

)
