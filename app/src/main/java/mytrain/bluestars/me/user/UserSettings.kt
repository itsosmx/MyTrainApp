package mytrain.bluestars.me.user

import android.content.Intent
import android.media.MediaSession2Service
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mytrain.bluestars.me.R

class UserSettings : AppCompatActivity() {
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
        notification = findViewById(R.id.notification)
        notification.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))

            // start your next activity*/
            startActivity(intent)

            privacy = findViewById(R.id.privacy)
            privacy.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://policies.google.com/privacy?hl=ar")
                )
                // start your next activity*/
                startActivity(intent)


                conditions = findViewById(R.id.conditions)
                conditions.setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/intl/ar/policies/terms/archive/20070416/")
                    )
                    // start your next activity*/
                    startActivity(intent)


                    share = findViewById(R.id.share)
                    var URI = "https://www.canva.com"

                    share.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.putExtra("share this", URI)
                        // start your next activity*/
                        startActivity(intent)

                        who = findViewById(R.id.who)
                        who.setOnClickListener {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/search/top?q=blue%20stars")
                            )
                            startActivity(intent)

                            ranning = findViewById(R.id.ranning)
                            ranning.setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.facebook.com/search/top?q=blue%20stars")
                                )
                                startActivity(intent)

                                evalute = findViewById(R.id.evalute)
                                evalute.setOnClickListener {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://support.google.com/googleplay/answer/6209544?hl=ar")
                                    )
                                    startActivity(intent)
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}