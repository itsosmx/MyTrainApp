package mytrain.bluestars.me.components

import android.app.Activity
import android.content.Intent

class Navigation {
    /**
     * Navigate to another activity
     * This is a custom function
     * @param context current activity (eg: this)
     * @param to the activity to go (eg: Home::class.java)
     */
    fun Navigate(context: Activity, to: Class<*>?) {
        val intent = Intent(context, to)
        context.startActivity(intent)
        context.finish()
    }
}