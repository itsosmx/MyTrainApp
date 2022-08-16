package mytrain.bluestars.me.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.paymob.acceptsdk.*
import mytrain.bluestars.me.Home
import mytrain.bluestars.me.R
import mytrain.bluestars.me.components.PaymobUtils
import mytrain.bluestars.me.components.Utils
import mytrain.bluestars.me.data.CitySpinnerData
import mytrain.bluestars.me.data.TicketData
import mytrain.bluestars.me.data.TrainData
import kotlin.Int

class TicketPayment : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    private lateinit var initTicket: TicketData
    private lateinit var ticketPayload: TicketData
    private lateinit var orderId: kotlin.String

    //Paymob
    private val acceptPaymentRequest = 12772020
    private var extra: Bundle = Bundle()
    private val showSaveCard = true

    /* Paymob
    * Card Number: 5123456789012346
    * Cardholder Name: Test Account
    * Expiry Month: 12
    * Expiry Year: 25
    * CVV: 123
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_payment)
        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance().getReference("")
        fAuth = FirebaseAuth.getInstance()

        val startStation: CitySpinnerData =
            intent.getSerializableExtra("start_station") as CitySpinnerData
        val endStation: CitySpinnerData =
            intent.getSerializableExtra("end_station") as CitySpinnerData

        val departureDate = intent.getStringExtra("departure_date")
        val travelerNumber = intent.getStringExtra("traveler_number")
        val ticketClass = intent.getStringExtra("ticket_class")
        val travelTime = intent.getStringExtra("travel_time")
        val trainId = intent.getStringExtra("train_id")
        val arrivalTime = intent.getStringExtra("arrival_time")
        val departureTime = intent.getStringExtra("departure_time")
        val trainType = intent.getStringExtra("type")
        val totalPrice = intent.getStringExtra("price")
        val uuid = Utils().GenerateID()

        initTicket = TicketData(
            uuid,
            startStation!!.id,
            endStation!!.id,
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
        PaymobUtils().getToken() { tokenResponse ->
            var amountCent = initTicket.amount?.toDouble()?.times(100)
            var quantity = initTicket.seats
            if (tokenResponse != null) {
                PaymobUtils().registerOrder(
                    tokenResponse.token,
                    amountCent.toString(),
                    quantity.toString()
                ) { orderResponse ->
                    if (orderResponse != null) {
                        PaymobUtils().paymentKey(
                            tokenResponse.token,
                            amountCent.toString(),
                            orderResponse.id.toString()
                        ) { paymentResponse ->
                            if (paymentResponse != null) {
                                orderId = orderResponse.id.toString()
                                payWithPaymob(paymentResponse.token)
                            } else Log.i("paymentResponse", "error")
                        }
                    } else Log.i("orderResponse", "error")
                }
            }
        }
    }

    private fun payWithPaymob(paymentKey: kotlin.String?) {
        val theme = ContextCompat.getColor(
            applicationContext,
            R.color.dark
        )
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey)
        intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false)
        intent.putExtra(PayActivityIntentKeys.THEME_COLOR, theme)
        intent.putExtra("ActionBar", true)
        intent.putExtra("language", "ar")
        startActivityForResult(intent, acceptPaymentRequest)
    }

    fun savedTestCard(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, "5123456789012346")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        extra = data?.extras!!
        if (requestCode == acceptPaymentRequest) {
            if (data != null) {
                when (resultCode) {
                    IntentConstants.USER_CANCELED -> {
                        ToastMaker.displayShortToast(this, "Canceled")
                    }
                    IntentConstants.MISSING_ARGUMENT -> {
                        ToastMaker.displayShortToast(
                            this,
                            "Missing ${extra.getString(IntentConstants.MISSING_ARGUMENT_VALUE)}"
                        )
                    }
                    IntentConstants.TRANSACTION_ERROR -> {
                        ToastMaker.displayShortToast(
                            this,
                            "Reason == " + extra.getString(IntentConstants.TRANSACTION_ERROR_REASON)
                        );
                    }
                    IntentConstants.TRANSACTION_REJECTED -> {
                        ToastMaker.displayShortToast(
                            this,
                            extra.getString(PayResponseKeys.DATA_MESSAGE)
                        );
                    }
                    IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE -> {
                        ToastMaker.displayShortToast(
                            this,
                            extra.getString(IntentConstants.RAW_PAY_RESPONSE)
                        );
                    }
                    IntentConstants.TRANSACTION_SUCCESSFUL -> {
                        ToastMaker.displayShortToast(
                            this,
                            extra.getString(PayResponseKeys.DATA_MESSAGE)
                        );
                        saveTransactionData(extra)
                    }
                    IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE -> {
                        saveTransactionData(extra)
//                        ToastMaker.displayShortToast(
//                            this,
//                            "TRANSACTION_SUCCESSFUL - Parsing Issue" + extra.getString(
//                                SaveCardResponseKeys.TOKEN
//                            )
//                        );
                    }
                    IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED -> {
                        Log.d(
                            "token",
                            "onActivityResult: " + extra.get(SaveCardResponseKeys.TOKEN)
                        );
                        Log.d(
                            "message",
                            "onActivityResult: " + extra.get(PayResponseKeys.MERCHANT_ORDER_ID)
                        );

                    }
                    IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION -> {
                        ToastMaker.displayShortToast(
                            this,
                            extra.getString(PayResponseKeys.PENDING)
                        );
                    }
                    IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE -> {
                        ToastMaker.displayShortToast(
                            this,
                            "User canceled 3-d secure verification - Parsing Issue!!"
                        )
                        ToastMaker.displayShortToast(
                            this,
                            extra.getString(IntentConstants.RAW_PAY_RESPONSE)
                        );
                    }
                }
            }
        }
    }

    private fun saveTransactionData(extra: Bundle) {
        val day: Long = 1000 * 60 * 60 * 24
        val validity: Long = System.currentTimeMillis() + day
        println(extra)
        ticketPayload = TicketData(
            id = initTicket.id,
            startStation = initTicket.startStation,
            endStation = initTicket.endStation,
            arrivalTime = initTicket.arrivalTime,
            departureTime = initTicket.departureTime,
            validity = validity,
            trainId = initTicket.trainId,
            seatClass = initTicket.seatClass,
            carNumber = initTicket.carNumber,
            seats = initTicket.seats,
            amount = initTicket.amount,
            departureDate = initTicket.departureDate,
            trainType = initTicket.trainType,
            order_id = orderId,
            createdAt = extra.getString(PayResponseKeys.CREATED_AT) ,
            status = extra.getString(PayResponseKeys.PENDING),
        )

        database.child("users").child(fAuth.currentUser!!.uid).child("tickets")
            .push().setValue(ticketPayload)
            .addOnSuccessListener {
                database.child("trains").child(ticketPayload.trainId.toString()).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            database.child("trains").child(ticketPayload.trainId.toString())
                                .child("tickets").push()
                                .setValue(ticketPayload.id)
                        } else {
                            database.child("trains").child(ticketPayload.trainId.toString())
                                .setValue(TrainData())
                                .addOnSuccessListener {
                                    database.child("trains").child(ticketPayload.trainId.toString())
                                        .child("tickets").push()
                                        .setValue(ticketPayload.id)
                                }
                        }
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                    }

            }
    }
    override fun onBackPressed() {
        return
    }

}


/*
*     private val CLIENT_ID =
        "ARx6_kgpBUlNI7fVZkNpLjWERIGdaqvfLOALTTkuW660PRKXBK_5_QDN0Ege951dwIU4zG0MaJKVvTI1"
    private val SECERT_KEY =
        "EOrTwOqICUYAkUh2kmMYz9_OajUH440ADAgfmkIDYz8IOa2VdtHf7-_WRvQx8DhEn1p2yRD-APyizoqy"
    private val PAYPAL_REQUEST_CODE = 123
    private val PAYPAL_ENV =
        com.paypal.android.sdk.payments.PayPalConfiguration.ENVIRONMENT_NO_NETWORK
    private lateinit var PayPalConfiguration: PayPalConfiguration


    PayPalConfiguration = PayPalConfiguration()
    .environment(PAYPAL_ENV)
    .clientId(CLIENT_ID)
    .merchantPrivacyPolicyUri(Uri.parse("https://www.mytrain.osmx.me/privacy"))
    .merchantUserAgreementUri(Uri.parse("https://www.mytrain.osmx.me/legal"))

    var services = Intent(this@TicketPayment, PayPalService::class.java)
    services.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfiguration)
    startService(services)
*
* */

//    override fun onDestroy() {
//        var services = Intent(this@TicketPayment, PayPalService::class.java)
//        stopService(services)
//        super.onDestroy()
//    }

//    private fun payWithPaypal() {
//        try {
//            val amount = ticketDate.price.toString()
//            val payment = PayPalPayment(
//                BigDecimal(String.valueOf(amount)), "USD", "Ticket",
//                PayPalPayment.PAYMENT_INTENT_SALE
//            )
//            val payIntent = Intent(this@TicketPayment, PaymentActivity::class.java)
//            payIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfiguration)
//            payIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//            startActivityForResult(payIntent, PAYPAL_REQUEST_CODE);
//        } catch (e: Exception) {
//            Log.e("Paypal", "Activity Error", e)
//        }
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == ACCEPT_PAYMENT_REQUEST) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                val confirm: PaymentConfirmation? = data?.getParcelableExtra(
//                    PaymentActivity.EXTRA_RESULT_CONFIRMATION
//                )
//                if (confirm != null) {
//                    try {
//                        val paymentDetails = confirm.toJSONObject().toString(4)
//                        val payObj = JSONObject(paymentDetails)
//                        val payID = payObj.getJSONObject("response").getString("id")
//                        val state = payObj.getJSONObject("response").getString("state")
//
//                        database.child("users").child(fAuth.currentUser!!.uid).child("tickets")
//                            .push()
//                            .setValue(ticketDate)
//                            .addOnSuccessListener {
//                                database.child("trains").child(ticketDate.trainId.toString()).push()
//                                    .setValue(TrainData("0", "0"))
//                                    .addOnCompleteListener {
//                                        database.child("trains")
//                                            .child(ticketDate.trainId.toString()).child("tickets")
//                                            .push()
//                                            .setValue(ticketDate.id.toString())
//                                            .addOnSuccessListener {
//                                                val intent =
//                                                    Intent(this@TicketPayment, Ticket::class.java)
//                                                intent.putExtra("ticket_id", ticketDate.id)
//                                                intent.putExtra(
//                                                    "start_station",
//                                                    ticketDate.startStation
//                                                )
//                                                intent.putExtra(
//                                                    "end_station",
//                                                    ticketDate.endStation
//                                                )
//                                                intent.putExtra(
//                                                    "departure_date",
//                                                    ticketDate.departureDate
//                                                )
//                                                intent.putExtra(
//                                                    "traveler_number",
//                                                    ticketDate.seats.toString()
//                                                )
//                                                intent.putExtra(
//                                                    "ticket_class",
//                                                    ticketDate.seatClass
//                                                )
//                                                intent.putExtra("train_id", ticketDate.trainId)
//                                                intent.putExtra(
//                                                    "arrival_time",
//                                                    ticketDate.arrivalTime
//                                                )
//                                                intent.putExtra(
//                                                    "departure_time",
//                                                    ticketDate.departureTime
//                                                )
//                                                intent.putExtra("train_type", ticketDate.trainType)
//                                                intent.putExtra("price", ticketDate.price)
//                                                startActivity(intent)
//                                            }
//                                    }
//                            }
//                    } catch (e: Exception) {
//                        Navigation().Message(this, "Something went Wrong")
//                        Log.e("Error", "an extremely unlikely failure occurred: ", e)
//                    }
//                }
//
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("paymentExample", "The user canceled.");
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                    "paymentExample",
//                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
//                );
//            }
//        }
//    }
