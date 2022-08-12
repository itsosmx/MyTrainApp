package mytrain.bluestars.me.data

import java.io.Serializable

data class CitySpinnerData(
    var id: String = "",
    var name: String = ""
): Serializable {
    override fun toString(): String {
        return name
    }
}