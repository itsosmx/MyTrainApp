package mytrain.bluestars.me.components

import android.app.Activity
import android.app.AlertDialog
import mytrain.bluestars.me.R

class LoadingDialog(val context: Activity) {
    private lateinit var alertDialog: AlertDialog

    /**
     * Start loading spinner
     */
    fun startLoading() {
        val inflate = context.layoutInflater
        val dialogView = inflate.inflate(R.layout.component_loading, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    /**
     * End loading spinner
     */
    fun endLoading() {
        alertDialog.dismiss()
    }
}