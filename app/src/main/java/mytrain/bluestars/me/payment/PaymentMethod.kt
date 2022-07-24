package mytrain.bluestars.me.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.paymob.acceptsdk.PayActivity
import com.paymob.acceptsdk.PayActivityIntentKeys
import com.paymob.acceptsdk.ThreeDSecureWebViewActivty
import mytrain.bluestars.me.R

class PaymentMethod : AppCompatActivity() {
    private lateinit var b_buy: Button
    private lateinit var b_cancel: Button
    val ACCEPT_PAYMENT_REQUEST = 10
    val PAYMENT_KEY = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6VXhNaUo5LmV5SjFjMlZ5WDJsa0lqb3pOVFUxTENKdmNtUmxjbDlwWkNJNk5qRXpNams0TUN3aVkzVnljbVZ1WTNraU9pSkZSMUFpTENKcGJuUmxaM0poZEdsdmJsOXBaQ0k2TkRnMU9Dd2liRzlqYTE5dmNtUmxjbDkzYUdWdVgzQmhhV1FpT21aaGJITmxMQ0ppYVd4c2FXNW5YMlJoZEdFaU9uc2labWx5YzNSZmJtRnRaU0k2SWtOc2FXWm1iM0prSWl3aWJHRnpkRjl1WVcxbElqb2lUbWxqYjJ4aGN5SXNJbk4wY21WbGRDSTZJa1YwYUdGdUlFeGhibVFpTENKaWRXbHNaR2x1WnlJNklqZ3dNamdpTENKbWJHOXZjaUk2SWpReUlpd2lZWEJoY25SdFpXNTBJam9pT0RBeklpd2lZMmwwZVNJNklrcGhjMnR2YkhOcmFXSjFjbWRvSWl3aWMzUmhkR1VpT2lKVmRHRm9JaXdpWTI5MWJuUnllU0k2SWtOU0lpd2laVzFoYVd3aU9pSmpiR0YxWkdWMGRHVXdPVUJsZUdFdVkyOXRJaXdpY0dodmJtVmZiblZ0WW1WeUlqb2lLemcyS0RncE9URXpOVEl4TURRNE55SXNJbkJ2YzNSaGJGOWpiMlJsSWpvaU1ERTRPVGdpTENKbGVIUnlZVjlrWlhOamNtbHdkR2x2YmlJNklrNUJJbjBzSW1WNGNDSTZNell3TURBd01EQXdNREF3TVRZd01ESTJNakF3T1N3aVlXMXZkVzUwWDJObGJuUnpJam8xTURBd01EQXNJbkJ0YTE5cGNDSTZJalF4TGpJek5pNHhOREF1TWpFMEluMC5yTXdsRXhiWF8xbFZsVHBIanhwaFNscDlLd3V1YzVnRVBfRy1xTFgtdHRnWW83SWJQNXpsTDFfS2RlYkxUeGlXMHl6WEM2Z29LbnVoQVAxMjV5RWExUQ==";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        b_buy = findViewById(R.id.b_buy)
        b_cancel = findViewById(R.id.b_cancel)


        b_buy.setOnClickListener {
            var intent = Intent(this@PaymentMethod, PayActivity::class.java)
            putNormalExtras(intent)
            intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
            intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, true)
            intent.putExtra(PayActivityIntentKeys.THEME_COLOR, R.color.dark)
            intent.putExtra("ActionBar", true)
            intent.putExtra("language", "ar")
            startActivityForResult(intent, ACCEPT_PAYMENT_REQUEST)
            var secure_intent = Intent(this@PaymentMethod, ThreeDSecureWebViewActivty::class.java)
            secure_intent.putExtra("ActionBar", true)
        }
    }
    private fun putNormalExtras(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, PAYMENT_KEY)
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification")
    }
}