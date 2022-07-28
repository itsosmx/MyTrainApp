package mytrain.bluestars.me.adapters
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mytrain.bluestars.me.R
import mytrain.bluestars.me.data.StationData

class StationAdapter(private val stationList:ArrayList<StationData>) : RecyclerView.Adapter<StationAdapter.MYViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.station_item,parent, false)
      return MYViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MYViewHolder, position: Int) {
val currentitem= stationList[position]

        holder.tv_id_item.text=currentitem.id.toString()
        holder.arrivalTime.text=currentitem.arrivalTime
        holder.departureTime.text= currentitem.departureTime
        holder.type.text = currentitem.type
        holder.price.text = currentitem.price.toString()


    }

    override fun getItemCount(): Int {
        return stationList.size
     }
    class MYViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tv_id_item:TextView=itemView.findViewById(R.id.tv_id_item)
        val departureTime:TextView=itemView.findViewById(R.id.tv_departureTime_item)
        val arrivalTime:TextView=itemView.findViewById(R.id.tv_arrivalTime_item)
        val type:TextView=itemView.findViewById(R.id.type_item)
        val price :TextView=itemView.findViewById(R.id.tv_price_item)





    }

}