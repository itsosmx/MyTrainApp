package mytrain.bluestars.me.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Navigation {
    /**
     * Navigate to another activity
     * This is a custom function
     * @param context current activity (eg: this)
     * @param to the activity to go (eg: Home::class.java)
     */
    fun Navigate(context: Context, to: Class<*>?) {
        val intent = Intent(context, to)
        context.startActivity(intent)
    }

    fun Message(context: Activity, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}