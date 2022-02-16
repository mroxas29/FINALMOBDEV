package com.example.firebaserecyclerviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val stationList : ArrayList<Station>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var mListener:onStationClickListener
    //Interface for cardview
    interface onStationClickListener{
        fun onStationClick(position:Int)


    }

    //Initializing listener
    fun setOnStationClickListener(listener: onStationClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.station,
        parent,false)
        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentstation = stationList[position]

        holder.stationName.text = currentstation.stationname
        holder.dieselPrice.text = currentstation.dieselPrice
        holder.unleadedPrice.text = currentstation.unleadedPrice
        holder.gasolinePrice.text = currentstation.gasolinePrice

    }

    override fun getItemCount(): Int {

        return stationList.size
    }


    class MyViewHolder(itemView : View, listener: onStationClickListener) : RecyclerView.ViewHolder(itemView){

        val stationName : TextView = itemView.findViewById(R.id.stationname)
        val dieselPrice : TextView = itemView.findViewById(R.id.dieselPrice)
        val unleadedPrice : TextView = itemView.findViewById(R.id.unleadedPrice)
        val gasolinePrice : TextView = itemView.findViewById(R.id.gasolinePrice)



        init{
            itemView.setOnClickListener {
                listener.onStationClick(adapterPosition )
            }
        }
    }

}