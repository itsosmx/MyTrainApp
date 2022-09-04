package mytrain.osmx.me.utils

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import mytrain.osmx.me.R

class CallUs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_us)
        supportActionBar?.title = "  تواصل معنا "


        val facebook = findViewById<ImageButton>(R.id.facebook)
        facebook?.setOnClickListener() {

            var url = "https://www.facebook.com/itsbluestars/";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }

        val youtube = findViewById<ImageButton>(R.id.youtube)
        youtube?.setOnClickListener() {

            var url = "https://www.youtube.com/channel/UC0mP0qMTlxbvTCimheSJmnQ";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }

        val twiter = findViewById<ImageButton>(R.id.twiter)
        twiter?.setOnClickListener() {

            var url = "https://twitter.com/itsbluestars";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }

        val instgram = findViewById<ImageButton>(R.id.instgram)
        instgram?.setOnClickListener() {

            var url = "https://www.instagram.com/itsbluestarsofficial/";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }








    }
}