package mytrain.osmx.me.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import mytrain.osmx.me.*
import mytrain.osmx.me.components.Navigation

class UserSettings : BaseActivity() {
    private lateinit var notification: Button
    private lateinit var privacy: Button
    private lateinit var conditions: Button
    private lateinit var share: Button
    private lateinit var who: Button
    private lateinit var ranning: Button
    private lateinit var evalute: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        supportActionBar?.title = "الإعدادات"

        notification = findViewById(R.id.notification)
        notification.setOnClickListener {
            val intent = Intent(this@UserSettings, UpdateData::class.java)
            // start your next activity*/
            startActivity(intent)
        }

        evalute = findViewById(R.id.evalute)
        evalute.setOnClickListener {
            var url =
                "https://play.google.com/store/apps/details?id=mytrain.osmx.me&hl=en-US&ah=GBf2-9R6RaMqR3CmGzrevwTc1yU";
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }
        share = findViewById(R.id.share)
        share.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Train || قطاري")
                var shareMessage = "\n تطبيق قطاري لحجز تذاكر وتتبع قطارات سكك حديد مصر بطريقة سهلة !! \n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=mytrain.osmx.me&hl=en-US&ah=GBf2-9R6RaMqR3CmGzrevwTc1yU${BuildConfig.APPLICATION_ID}
                    
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }
        who = findViewById(R.id.who)
        who.setOnClickListener {
            Navigation().Navigate(this@UserSettings, WeAre::class.java)
        }

        ranning = findViewById(R.id.ranning)
        ranning.setOnClickListener {
            Navigation().Navigate(this@UserSettings, callUs::class.java)
        }

        conditions = findViewById(R.id.conditions)
        conditions.setOnClickListener {
            Navigation().Navigate(this@UserSettings, mytrain.osmx.me.conditions::class.java)
        }

        privacy = findViewById(R.id.privacy)
        privacy.setOnClickListener {
            Navigation().Navigate(this@UserSettings, Privacy::class.java)
        }









    }
}

