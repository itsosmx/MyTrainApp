package mytrain.osmx.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TrainData(
    val latitude: Double?,
    val longitude: Double?,
    val seats: Int? = 100,
    val unavailable: Int?,
    val tickets: List<TicketPayload>? = null
)

@IgnoreExtraProperties
data class TicketPayload(
    val id: String?,
    val seats: Int?
)

@IgnoreExtraProperties
data class TicketUnavailableSeats(
    val seats: Int? = 0,
    val unavailable: Int? = 0
)