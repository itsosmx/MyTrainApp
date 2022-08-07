package mytrain.bluestars.me.components

import android.content.Context
import android.util.Log
import org.json.JSONObject
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

interface ApiCalls {
    @Headers("Content-Type: application/json")
    @POST("auth/tokens")
    fun token(@Body data: TokenData): Call<TokenResponseBody>

    @POST("ecommerce/orders")
    fun order(@Body data: OrderRegistrationRequest): Call<OrderRegistrationResponse>

    @POST("acceptance/payment_keys")
    fun payment(@Body data: PaymentKeyRequest): Call<PaymentKeyResponse>

}

object Build {
    private const val url = "https://accept.paymob.com/api/"
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

class PaymobUtils {
    fun getToken(onResult: (TokenResponseBody?) -> Unit) {
        try {
            val retrofit = Build.buildService(ApiCalls::class.java)
            val data = TokenData()
            retrofit.token(data).enqueue(
                object : Callback<TokenResponseBody> {
                    override fun onResponse(
                        call: Call<TokenResponseBody>,
                        response: Response<TokenResponseBody>
                    ) {
                        val res = response.body()
                        onResult(res)
                        println("Token Call Success")
                    }

                    override fun onFailure(call: Call<TokenResponseBody>, t: Throwable) {
                        Log.e("Error", "TokenResponseBody", t)
                        onResult(null)
                    }

                }
            )
        } catch (e: Exception) {
            Log.e("Error", "Token", e)
        }
    }

    fun registerOrder(
        token: String?,
        amount_cents: String?,
        quantity: String?,
        onResult: (OrderRegistrationResponse?) -> Unit
    ) {
        try {
            val retrofit = Build.buildService(ApiCalls::class.java)
            val items = Items("Ticket", amount_cents, "train ticket", quantity)
            val ar = Array(1) {
                items
            }
            val data = OrderRegistrationRequest(
                auth_token = token,
                delivery_needed = false,
                amount_cents = amount_cents,
                items = ar
            )
            retrofit.order(data).enqueue(object : Callback<OrderRegistrationResponse> {
                override fun onResponse(
                    call: Call<OrderRegistrationResponse>,
                    response: Response<OrderRegistrationResponse>
                ) {
                    val res = response.body()
                    onResult(res)
                    println("Order Register Success")
                }

                override fun onFailure(call: Call<OrderRegistrationResponse>, t: Throwable) {
                    onResult(null)
                }
            })
        } catch (e: Exception) {
            Log.e("Error", "registerOrder", e)
        }
    }

    fun paymentKey(
        token: String?,
        amount_cents: String?,
        order_id: String?,
        onResult: (PaymentKeyResponse?) -> Unit
    ) {
        try {
            val retrofit = Build.buildService(ApiCalls::class.java)
            val billing = BillingData()
            val data = PaymentKeyRequest(
                auth_token = token,
                amount_cents = amount_cents,
                expiration = 60 * 60 * 60 * 24,
                order_id = order_id,
                billing_data = billing,
                currency = "EGP",
            )
            retrofit.payment(data).enqueue(object : Callback<PaymentKeyResponse> {
                override fun onResponse(
                    call: Call<PaymentKeyResponse>,
                    response: Response<PaymentKeyResponse>
                ) {
                    val res = response.body()
                    println("Payment Key Call Success")
                    onResult(res)
                }

                override fun onFailure(call: Call<PaymentKeyResponse>, t: Throwable) {
                    onResult(null)
                }

            })

        } catch (e: Exception) {
            Log.e("Error", "registerOrder", e)
        }
    }
}

data class TokenData(
    @SerializedName("api_key") val api_key: String = "ZXlKaGJHY2lPaUpJVXpVeE1pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmpiR0Z6Y3lJNklrMWxjbU5vWVc1MElpd2ljSEp2Wm1sc1pWOXdheUk2TWpRME5EUTJMQ0p1WVcxbElqb2lNVFkxT1RjNE16QXhNQzQwTURnMU56RWlmUS43eThNUUlrd2w0d1QxWXN0Rngyc1BLZERnNWk3WXlzclR1SUtHb1FCV1gxUk9BV0RSYUZhMEFzOVF2dktscVREQXQ4VzZRN1NEMUhNaVBHUnVtME5qZw=="
)

data class TokenResponseBody(
    @SerializedName("token") val token: String? = null
)

data class Items(
    @SerializedName("name") val name: String? = null,
    @SerializedName("amount_cents") val amount_cents: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("quantity") val quantity: String? = null,
)

data class OrderRegistrationRequest(
    @SerializedName("auth_token") val auth_token: String? = null,
    @SerializedName("delivery_needed") val delivery_needed: Boolean = false,
    @SerializedName("amount_cents") val amount_cents: String? = null,
    @SerializedName("items") val items: Array<Items>,
)

data class OrderRegistrationResponse(
    @SerializedName("id") val id: Int? = null,
)

data class BillingData(
    @SerializedName("apartment") val apartment: String = "803",
    @SerializedName("email") val email: String = "itsosmx@gmail.com",
    @SerializedName("floor") val floor: String = "42",
    @SerializedName("first_name") val first_name: String = "Clifford",
    @SerializedName("street") val street: String = "Ethan Land",
    @SerializedName("building") val building: String = "8028",
    @SerializedName("phone_number") val phone_number: String = "+86(8)9135210487",
    @SerializedName("shipping_method") val shipping_method: String = "PKG",
    @SerializedName("postal_code") val postal_code: String = "01898",
    @SerializedName("city") val city: String = "Jaskolskiburgh",
    @SerializedName("country") val country: String = "CR",
    @SerializedName("last_name") val last_name: String = "Nicolas",
    @SerializedName("state") val state: String = "Utah",
    )

data class PaymentKeyRequest(
    @SerializedName("auth_token") val auth_token: String? = null,
    @SerializedName("amount_cents") val amount_cents: String? = null,
    @SerializedName("expiration") val expiration: Int? = null,
    @SerializedName("order_id") val order_id: String? = null,
    @SerializedName("billing_data") val billing_data: BillingData?,
    @SerializedName("currency") val currency: String? = "EGP",
    @SerializedName("integration_id") val integration_id: Int? = 2422117,
    @SerializedName("lock_order_when_paid") val lock_order_when_paid: String? = "false"
)

data class PaymentKeyResponse(
    @SerializedName("token") val token: String? = null,
)