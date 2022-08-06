package mytrain.bluestars.me.payment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.paymob.acceptsdk.*
import com.paypal.android.sdk.payments.*
import mytrain.bluestars.me.R
import mytrain.bluestars.me.components.Utils
import mytrain.bluestars.me.data.TicketData
import org.json.JSONObject
import java.lang.String
import java.math.BigDecimal
import kotlin.Exception
import kotlin.Int

class TicketPayment : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    private lateinit var ticketDate: TicketData

    //Paymob
    private val ACCEPT_PAYMENT_REQUEST = 123
    private val paymentKey =
        "ZXlKaGJHY2lPaUpJVXpVeE1pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnVZVzFsSWpvaU1UWTFPVFV4T1RrME5DNDVORGc1T1RRaUxDSndjbTltYVd4bFgzQnJJam95TkRRME5EWXNJbU5zWVhOeklqb2lUV1Z5WTJoaGJuUWlmUS5lSGx0ZnVVbmljVmtPUVNwd2YyMUs3LTJmcjdGaGVSdVNFem4zdDdKQTZ1ZGNWVXNVcTRkS2I2OEFGWndkWVBjNy1wcjJacjZDT2NOemMzV0RIVXktdw=="
    private var extra: Bundle = Bundle()
    private val showSaveCard = true

    /* Paymob
    * Card Number: 5123456789012346
    * Cardholder Name: Test Account
    * Expiry Month: 12
    * Expiry Year: 25
    * CVV: 123
    * */

    /* Paypal
    * Test Account
    * Email: test@osmx.me
    * Password: @Osama1277
    * */
    private val CLIENT_ID =
        "ARx6_kgpBUlNI7fVZkNpLjWERIGdaqvfLOALTTkuW660PRKXBK_5_QDN0Ege951dwIU4zG0MaJKVvTI1"
    private val SECERT_KEY =
        "EOrTwOqICUYAkUh2kmMYz9_OajUH440ADAgfmkIDYz8IOa2VdtHf7-_WRvQx8DhEn1p2yRD-APyizoqy"
    private val PAYPAL_REQUEST_CODE = 123
    private val PAYPAL_ENV =
        com.paypal.android.sdk.payments.PayPalConfiguration.ENVIRONMENT_NO_NETWORK
    private lateinit var PayPalConfiguration: PayPalConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)
        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance().getReference("")
        fAuth = FirebaseAuth.getInstance()

        PayPalConfiguration = PayPalConfiguration()
            .environment(PAYPAL_ENV)
            .clientId(CLIENT_ID)
            .merchantPrivacyPolicyUri(Uri.parse("https://www.mytrain.osmx.me/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.mytrain.osmx.me/legal"))

        var services = Intent(this@TicketPayment, PayPalService::class.java)
        services.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfiguration)
        startService(services)

        val startStation = intent.getStringExtra("start_station")
        val endStation = intent.getStringExtra("end_station")
        val departureDate = intent.getStringExtra("departure_date")
        val travelerNumber = intent.getStringExtra("traveler_number")
        val ticketClass =  intent.getStringExtra("ticket_class")
        val travelTime = intent.getStringExtra("travel_time")
        val trainId = intent.getStringExtra("train_id")
        val arrivalTime =  intent.getStringExtra("arrival_time")
        val departureTime = intent.getStringExtra("departure_time")
        val trainType = intent.getStringExtra("type")
        val totalPrice = intent.getStringExtra("price")
        val uuid = Utils().GenerateID()

        ticketDate = TicketData(
            uuid,
            startStation.toString(),
            endStation.toString(),
            arrivalTime.toString(),
            departureTime.toString(),
            null,
            trainId.toString(),
            ticketClass.toString(),
            null,
            travelerNumber?.toInt(),
            totalPrice.toString(),
            departureDate.toString(),
            trainType
        )
        payWithPaypal()
    }

    override fun onDestroy() {
        var services = Intent(this@TicketPayment, PayPalService::class.java)
        stopService(services)
        super.onDestroy()
    }

    private fun payWithPaypal() {
        try {
            val amount = ticketDate.price.toString()
            val payment = PayPalPayment(
                BigDecimal(String.valueOf(amount)), "USD", "Ticket",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            val payIntent = Intent(this@TicketPayment, PaymentActivity::class.java)
            payIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfiguration)
            payIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(payIntent, PAYPAL_REQUEST_CODE);
        } catch (e: Exception) {
            Log.e("Paypal", "Activity Error", e)
        }
    }

    private fun payWithPaymob() {
        val theme = ContextCompat.getColor(
            applicationContext,
            R.color.dark
        )
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey)
        intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard)
        intent.putExtra(PayActivityIntentKeys.THEME_COLOR, theme)
        intent.putExtra("ActionBar", true)
        intent.putExtra("language", "ar")
        startActivityForResult(intent, ACCEPT_PAYMENT_REQUEST)
    }

    fun savedTestCard(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, "5123456789012346")
//        intent.putExtra(PayActivityIntentKeys.)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {

                val confirm: PaymentConfirmation? = data?.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION
                )
                if (confirm != null) {
                    try {
                        val paymentDetails = confirm.toJSONObject().toString(4)
                        val payObj = JSONObject(paymentDetails)
                        val payID = payObj.getJSONObject("response").getString("id")
                        val state = payObj.getJSONObject("response").getString("state")

                        database.child("users").child(fAuth.currentUser!!.uid).child("tickets")
                            .push()
                            .setValue(ticketDate)
                            .addOnCompleteListener {
                                var intent = Intent(this@TicketPayment, Ticket::class.java)
                                intent.putExtra("start_station", ticketDate.startStation)
                                intent.putExtra("end_station", ticketDate.endStation)
                                intent.putExtra("departure_date", ticketDate.departureDate)
                                intent.putExtra("traveler_number", ticketDate.seats.toString())
                                intent.putExtra("ticket_class", ticketDate.seatClass)
                                intent.putExtra("train_id", ticketDate.trainId)
                                intent.putExtra("arrival_time", ticketDate.arrivalTime)
                                intent.putExtra("departure_time", ticketDate.departureTime)
                                intent.putExtra("train_type", ticketDate.trainType)
                                intent.putExtra("price", ticketDate.price)
                                startActivity(intent)
                            }
                    } catch (e: Exception) {
                        Log.e("Error", "an extremely unlikely failure occurred: ", e)
                    }
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                    "paymentExample",
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
                );
            }
        }
    }
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        extra = data?.extras!!
        if (requestCode == ACCEPT_PAYMENT_REQUEST) {
            if (data != null) {
                // User canceled and did no payment request was fired
                if (resultCode == IntentConstants.USER_CANCELED) {
                    ToastMaker.displayShortToast(this, "Canceled")
                }
                else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                    ToastMaker.displayShortToast(this, "Missing ${extra.getString(IntentConstants.MISSING_ARGUMENT_VALUE)}")
                }
                else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                    ToastMaker.displayShortToast(this, "Reason == " + extra.getString(IntentConstants.TRANSACTION_ERROR_REASON));
                }
                else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                    ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.DATA_MESSAGE));
                }
                else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                    ToastMaker.displayShortToast(this, extra.getString(IntentConstants.RAW_PAY_RESPONSE));
                }
                else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                    ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.DATA_MESSAGE));
                }
                else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                    ToastMaker.displayShortToast(this, "TRANSACTION_SUCCESSFUL - Parsing Issue" + extra.getString(SaveCardResponseKeys.TOKEN));
                }
                else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
//                ToastMaker.displayShortToast(this, "Token == " + extra.getString(SaveCardResponseKeys.TOKEN));
//                ToastMaker.displayLongToast(this, "data " + extra.getString(PayResponseKeys.DATA_MESSAGE));
                    Log.d("token", "onActivityResult: " + extra.get(SaveCardResponseKeys.TOKEN));
                    Log.d("message", "onActivityResult: " + extra.get(PayResponseKeys.MERCHANT_ORDER_ID));
                }
                else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                    ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.PENDING));
                }
                else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                    ToastMaker.displayShortToast(this, "User canceled 3-d scure verification - Parsing Issue!!")
                    ToastMaker.displayShortToast(this, extra.getString(IntentConstants.RAW_PAY_RESPONSE));
                }
            }
        }
    }*/
}