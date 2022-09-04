package mytrain.osmx.me.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mytrain.osmx.me.R

class Conditions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conditions)
        supportActionBar?.title = " الاحكام والشروط "

    }
}