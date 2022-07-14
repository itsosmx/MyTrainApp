package mytrain.bluestars.me.components

import android.app.Activity
import android.content.Intent

class Navigation {
    fun Navigate(context: Activity, to: Class<*>?) {
        val intent = Intent(context, to)
        context.startActivity(intent)
    }
}