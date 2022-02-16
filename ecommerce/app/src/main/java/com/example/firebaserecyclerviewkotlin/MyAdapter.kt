package com.example.firebaserecyclerviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val productList : ArrayList<Product>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var mListener:onStationClickListener
    interface onStationClickListener{
        fun onStationClick(position:Int)


    }

    fun setOnStationClickListener(listener: onStationClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product,
        parent,false)
        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentstation = productList[position]

        holder.productName.text = currentstation.productName
        holder.price.text = currentstation.Price
        holder.productDescription.text = currentstation.productDescription


    }

    override fun getItemCount(): Int {

        return productList.size
    }


    class MyViewHolder(itemView : View, listener: onStationClickListener) : RecyclerView.ViewHolder(itemView){

        val productName : TextView = itemView.findViewById(R.id.productName)
        val price : TextView = itemView.findViewById(R.id.Price)
        val productDescription : TextView = itemView.findViewById(R.id.productDescription)




        init{
            itemView.setOnClickListener {
                listener.onStationClick(adapterPosition )
            }
        }
    }

}