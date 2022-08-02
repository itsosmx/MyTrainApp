package mytrain.bluestars.me.payment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.paymob.acceptsdk.PayActivity
import com.paymob.acceptsdk.PayActivityIntentKeys
import com.paypal.android.sdk.payments.*
import mytrain.bluestars.me.R
import org.json.JSONObject
import java.lang.String
import java.math.BigDecimal
import kotlin.Exception
import kotlin.Int
import com.paypal.android.sdk.payments.PayPalConfiguration


class PaymentMethod : AppCompatActivity() {
    private lateinit var b_buy: Button
    private lateinit var b_cancel: Button

    //Paymob
    private val ACCEPT_PAYMENT_REQUEST = 123
    private val PAYMENT_KEY = "ZXlKaGJHY2lPaUpJVXpVeE1pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmpiR0Z6Y3lJNklrMWxjbU5vWVc1MElpd2ljSEp2Wm1sc1pWOXdheUk2TWpRME5EUTJMQ0p1WVcxbElqb2lNVFkxT1RBNU1Ua3dNeTQxTnprd01qa2lmUS5VMjVHSERhb0FDUWVoaUIzN2FMS0dYaWQ4Vm1ZVlROV19zdmp0My1pNXN6U0JJUjdyb0dWeGpPZ1UwelczR2tuYTdOVlY5OHdlOVMwdUF4QlFWSHJ5UQ==";
    private var extra: Bundle = Bundle()
    private val showSaveCard = true


    //Paypal
    /*
    * Test Account
    * Email: sb-fukz88038515@personal.example.com
    * Password: SKct=?C0 [or] osama1277
    *
    * */
    private val CLIENT_ID = "ARx6_kgpBUlNI7fVZkNpLjWERIGdaqvfLOALTTkuW660PRKXBK_5_QDN0Ege951dwIU4zG0MaJKVvTI1"
    private val SECERT_KEY = "EOrTwOqICUYAkUh2kmMYz9_OajUH440ADAgfmkIDYz8IOa2VdtHf7-_WRvQx8DhEn1p2yRD-APyizoqy"
    private val PAYPAL_REQUEST_CODE = 123
    private val PAYPAL_ENV = com.paypal.android.sdk.payments.PayPalConfiguration.ENVIRONMENT_NO_NETWORK
    private val PayPalConfiguration = PayPalConfiguration()
        .environment(PAYPAL_ENV)
        .clientId(CLIENT_ID)
        .merchantPrivacyPolicyUri(Uri.parse("https://www.mytrain.osmx.me/privacy"))
        .merchantUserAgreementUri(Uri.parse("https://www.mytrain.osmx.me/legal"))



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        b_buy = findViewById(R.id.b_buy)
        b_cancel = findViewById(R.id.b_cancel)



        b_buy.setOnClickListener {
            try {
                payWithPaypal()
            } catch (error: Exception) {
                Log.e("Payment Error ->", error.toString())
            }
        }

    }

    private fun payWithPaypal() {
        try {
            val amount = "50"
            val payment = PayPalPayment(
                BigDecimal(String.valueOf(amount)), "USD", "Ticket",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            val intent = Intent(this@PaymentMethod, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfiguration)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        } catch (e: Exception) {

        }
    }

    fun payWithPaymob() {
        val Theme = ContextCompat.getColor(applicationContext,
            R.color.dark)
        val pay_intent = Intent(this, PayActivity::class.java)
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
        pay_intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, PAYMENT_KEY)
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard)
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, Theme)
        pay_intent.putExtra("ActionBar", true)
        pay_intent.putExtra("language", "ar")
        startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST)
    }

    fun savedTestCard(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, "5123456789012346")
//        intent.putExtra(PayActivityIntentKeys.)
    }
    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        extra = data?.extras!!
        if (resultCode  == Activity.RESULT_OK) {
            val confirm: PaymentConfirmation? = data.getParcelableExtra(
                PaymentActivity.EXTRA_RESULT_CONFIRMATION
            )
            if (confirm != null) {
                try {
                    val paymentDetails = confirm.toJSONObject().toString(4)
                    val payObj = JSONObject(paymentDetails)
                    val payID = payObj.getJSONObject("response").getString("id")
                    val state = payObj.getJSONObject("response").getString("state")
                    Log.i("ID", payID)
                    Log.i("State", state)
                } catch (e: Exception) {
                    Log.e("Error", "an extremely unlikely failure occurred: ", e)
                }
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

//    override fun onActivityReenter(resultCode: Int, data: Intent?) {
//        super.onActivityReenter(resultCode, data)
//        extra = data?.extras!!
//
//        if (data != null) {
//            // User canceled and did no payment request was fired
//            if (resultCode == IntentConstants.USER_CANCELED) {
//                ToastMaker.displayShortToast(this, "Canceled")
//            }
//            else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
//                ToastMaker.displayShortToast(this, "Missing ${extra.getString(IntentConstants.MISSING_ARGUMENT_VALUE)}")
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
//                ToastMaker.displayShortToast(this, "Reason == " + extra.getString(IntentConstants.TRANSACTION_ERROR_REASON));
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
//                ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.DATA_MESSAGE));
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
//                ToastMaker.displayShortToast(this, extra.getString(IntentConstants.RAW_PAY_RESPONSE));
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
//                ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.DATA_MESSAGE));
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
//                ToastMaker.displayShortToast(this, "TRANSACTION_SUCCESSFUL - Parsing Issue" + extra.getString(SaveCardResponseKeys.TOKEN));
//            }
//            else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
////                ToastMaker.displayShortToast(this, "Token == " + extra.getString(SaveCardResponseKeys.TOKEN));
////                ToastMaker.displayLongToast(this, "data " + extra.getString(PayResponseKeys.DATA_MESSAGE));
//                Log.d("token", "onActivityResult: " + extra.get(SaveCardResponseKeys.TOKEN));
//                Log.d("message", "onActivityResult: " + extra.get(PayResponseKeys.MERCHANT_ORDER_ID));
//            }
//            else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
//                ToastMaker.displayShortToast(this, extra.getString(PayResponseKeys.PENDING));
//            }
//            else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
//                ToastMaker.displayShortToast(this, "User canceled 3-d scure verification - Parsing Issue!!")
//                ToastMaker.displayShortToast(this, extra.getString(IntentConstants.RAW_PAY_RESPONSE));
//            }
//        }
//    }
}