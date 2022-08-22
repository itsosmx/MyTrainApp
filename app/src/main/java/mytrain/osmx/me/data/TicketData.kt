package mytrain.osmx.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TicketData(
    val id: String? = null,
    val startStation: String? = null,
    val endStation: String? = null,
    val arrivalTime: String? = null,
    val departureTime: String? = null,
    val validity: Long? = null,
    val trainId: String? = null,
    val seatClass: String? = null,
    val carNumber: String? = null,
    val seats: Int? = null,
    val amount: String? = null,
    val departureDate: String? = null,
    val trainType: String? = null,
    val order_id: String? = null,
    val status: String? = null,
    val transactions_id: String? = null,
    val createdAt: String? = null

)
