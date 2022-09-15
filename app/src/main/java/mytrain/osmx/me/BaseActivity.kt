package mytrain.osmx.me

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mytrain.osmx.me.auth.Login
import mytrain.osmx.me.components.LoadingDialog
import mytrain.osmx.me.components.Navigation
import mytrain.osmx.me.payment.MyTicketsList
import mytrain.osmx.me.payment.TicketPicker
import mytrain.osmx.me.user.UserSettings

open class BaseActivity : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        loading = LoadingDialog(this)
        fAuth = FirebaseAuth.getInstance()
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        loading.startLoading()
        return when (item.itemId) {

            R.id.nav_home -> {
                Navigation().Navigate(this, Home::class.java)
                true
            }
            R.id.nav_buy_ticket -> {
                Navigation().Navigate(this, TicketPicker::class.java)
                true
            }
            R.id.nav_my_ticket -> {
                Navigation().Navigate(this, MyTicketsList::class.java)
                true
            }
            R.id.nav_settings -> {
                Navigation().Navigate(this, UserSettings::class.java)
                true
            }
            R.id.nav_share -> {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Train || قطاري")
                    var shareMessage = "\n تطبيق قطاري لحجز تذاكر وتتبع قطارات سكك حديد مصر بطريقة سهلة !! \n\n"
                    shareMessage =
                        """
                    ${shareMessage}https://play.google.com/store/apps/details?id=mytrain.osmx.me
                    """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {
                    //e.toString();
                }
                true
            }
            R.id.nav_rate -> {
                var url =
                    "https://play.google.com/store/apps/details?id=mytrain.osmx.me&hl=en-US&ah=GBf2-9R6RaMqR3CmGzrevwTc1yU";
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                })
                true
            }
            R.id.nav_suggestions -> {
                Navigation().Navigate(this, Suggestions::class.java)
                true
            }
            R.id.nav_signout -> {
                fAuth.signOut()
                Navigation().Navigate(this, Login::class.java)
                loading.endLoading()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onBackPressed() {
//        return
//    }
}