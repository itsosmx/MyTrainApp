package mytrain.bluestars.me.data

data class cityes(
    var id: String = "",
    var name: String = ""
) {

    override fun toString(): String {
        return name
    }

}