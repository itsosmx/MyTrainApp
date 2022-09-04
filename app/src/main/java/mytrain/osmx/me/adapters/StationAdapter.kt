package mytrain.osmx.me.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mytrain.osmx.me.R
import mytrain.osmx.me.data.CitySpinnerData
import mytrain.osmx.me.data.StationData
import mytrain.osmx.me.payment.TicketInfo
import java.math.RoundingMode
import java.text.DecimalFormat

class StationAdapter(private val stationList: ArrayList<StationData>, val intent: Intent) :
    RecyclerView.Adapter<StationAdapter.MYViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false)
        return MYViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MYViewHolder, position: Int) {
        val current = stationList[position]
        val start_station: CitySpinnerData =
            intent.getSerializableExtra("start_station") as CitySpinnerData
        val end_station: CitySpinnerData =
            intent.getSerializableExtra("end_station") as CitySpinnerData
        val departureDate = intent.getStringExtra("date")
        val travelerNumber = intent.getStringExtra("traveler_number")
        val ticketClass = intent.getStringExtra("ticket_class")
        val startTime = intent.getStringExtra("start_time")

        holder.tv_ticket_id.text = current.id
        holder.tv_ticket_arrival_time.text = current.arrivalTime
        holder.tv_ticket_departure_time.text = current.departureTime
        holder.tv_ticket_class.text = current.type
        holder.tv_ticket_price.text = current.price.toString() + " EGP"

        val timedPrice = current.price?.times(travelerNumber!!.toInt())
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.UP
        val calcPrice = df.format(timedPrice!!.toDouble().times(100))

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, TicketInfo::class.java)
            intent.putExtra("start_station", start_station)
            intent.putExtra("end_station", end_station)
            intent.putExtra("departure_date", departureDate)
            intent.putExtra("traveler_number", travelerNumber)
            intent.putExtra("ticket_class", ticketClass)
            intent.putExtra("start_time", startTime)
            intent.putExtra("train_id", current.id.toString())
            intent.putExtra("arrival_time", current.arrivalTime)
            intent.putExtra("departure_time", current.departureTime)
            intent.putExtra("type", current.type)
            intent.putExtra("price", calcPrice.toString())
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    class MYViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_ticket_id: TextView = itemView.findViewById(R.id.tv_ticket_id)
        val tv_ticket_arrival_time: TextView = itemView.findViewById(R.id.tv_ticket_arrival_time)
        val tv_ticket_departure_time: TextView =
            itemView.findViewById(R.id.tv_ticket_departure_time)
        val tv_ticket_class: TextView = itemView.findViewById(R.id.tv_ticket_class)
        val tv_ticket_price: TextView = itemView.findViewById(R.id.tv_ticket_price)
    }
}