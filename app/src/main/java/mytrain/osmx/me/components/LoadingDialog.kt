package mytrain.osmx.me.components

import android.app.Activity
import android.app.AlertDialog
import mytrain.osmx.me.R

class LoadingDialog(val context: Activity) {

    /**
     * Start loading spinner
     */

    private lateinit var alertDialog: AlertDialog

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