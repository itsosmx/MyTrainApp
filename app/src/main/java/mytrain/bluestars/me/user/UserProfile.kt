package mytrain.bluestars.me.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R

class UserProfile : AppCompatActivity() {
    private lateinit var setting: Button
    private lateinit var bt_logoff: Button
    private lateinit var b_submit: Button
    // private lateinit var et_username_input: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        bt_logoff = findViewById(R.id.bt_logoff)
        setting = findViewById(R.id.setting)
        b_submit = findViewById(R.id.b_submit)
        val et_username_input = findViewById<EditText>(R.id.et_username_input)
        setting.setOnClickListener {
            val intent = Intent(this@UserProfile, UserSettings::class.java)
            startActivity(intent)


        }
        bt_logoff.setOnClickListener {
            val intent = Intent(this@UserProfile, Home::class.java)
            startActivity(intent)


        }
        /*    b_submit.setOnClickListener {
                val intent = Intent(this@UserProfile, UserSettings::class.java)
                intent.putExtra("from", et_username_input.toString())
                startActivity(intent)*/
    }

}
