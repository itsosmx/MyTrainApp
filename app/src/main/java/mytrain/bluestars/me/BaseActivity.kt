package mytrain.bluestars.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mytrain.bluestars.me.auth.Login
import mytrain.bluestars.me.components.LoadingDialog
import mytrain.bluestars.me.components.Navigation
import mytrain.bluestars.me.payment.MyTicketsList
import mytrain.bluestars.me.payment.TicketPicker
import mytrain.bluestars.me.user.UserSettings

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
            R.id.nav_notification -> {
                Toast.makeText(this, "No screen for this", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_home -> {
                Navigation().Navigate(this, Home::class.java)
                Toast.makeText(this, "No screen for this", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "No screen for this", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_rate -> {
                Toast.makeText(this, "No screen for this", Toast.LENGTH_SHORT).show()
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