package mytrain.bluestars.me.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TicketData(
    val t_id: String? = null,
    val t_from: String? = null,
    val t_to: String? = null,
    val t_validity: String? = null,
    val t_train_id: String? = null,
    val t_degree: String? = null,
    val t_car_number: String? = null,
    val t_seat: String? = null,
    val t_price: String? = null
)
