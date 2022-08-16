package mytrain.bluestars.me

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import mytrain.bluestars.me.components.Navigation

class News : AppCompatActivity() {
    private lateinit var b_table: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        b_table = findViewById(R.id.b_table)

        val button6 = findViewById<ImageButton>(R.id.button6)
        button6?.setOnClickListener() {

            var url = "https://www.enr.gov.eg/Ar/News.aspx";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })

        }
        b_table.setOnClickListener {
            Navigation().Navigate(this@News, TimeTable::class.java)


        }
    }
}

