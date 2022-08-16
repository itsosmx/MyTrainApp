
package mytrain.bluestars.me.payment
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import mytrain.bluestars.me.R
import mytrain.bluestars.me.data.TicketData

//import mytrain.bluestars.me.payment.TicketInfo

class MyTicketAdapter(private val stationList:ArrayList<TicketData>, val intent: Intent) : RecyclerView.Adapter<MyTicketAdapter.MYViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_ticket_item,parent, false)
        return MYViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MYViewHolder, position: Int) {
        val current = stationList[position]

        holder.tv_startStation.text= current.startStation
        holder.tv_endStation.text= current.endStation
        holder.tv_arrivalTime.text= current.arrivalTime
        holder.tv_departureTime.text= current.departureTime
        holder.tv_departureDate.text= current.departureDate
        holder.tv_seatClass.text= current.seatClass
        holder.tv_seats.text= current.seats.toString()
        holder.tv_order_id.text= current.order_id
        holder.tv_trainId.text= current.trainId
        holder.tv_validity.text= current.validity.toString()
        holder.tv_amount.text= current.amount








        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, Ticket::class.java)

            it.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return stationList.size
    }


    class MYViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        val tv_startStation:TextView=itemView.findViewById(R.id.tv_startStation)
        val tv_endStation:TextView=itemView.findViewById(R.id.tv_endStation)
        val tv_arrivalTime:TextView=itemView.findViewById(R.id.tv_arrivalTime)
        val tv_departureTime:TextView=itemView.findViewById(R.id.tv_departureTime)
        val tv_departureDate:TextView=itemView.findViewById(R.id.tv_departureDate)
        val tv_seatClass:TextView=itemView.findViewById(R.id.tv_seatClass)
        val tv_seats:TextView=itemView.findViewById(R.id.tv_seats)
        val tv_order_id:TextView=itemView.findViewById(R.id.tv_order_id)
        val tv_trainId:TextView=itemView.findViewById(R.id.tv_trainId)
        val tv_validity:TextView=itemView.findViewById(R.id.tv_validity)
        val tv_amount:TextView=itemView.findViewById(R.id.tv_amount)





    }
}
