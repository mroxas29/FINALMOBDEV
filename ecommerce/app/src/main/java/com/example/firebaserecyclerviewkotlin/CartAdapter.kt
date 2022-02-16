package com.example.firebaserecyclerviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartList: ArrayList<cartItem>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private lateinit var mListener:onCartClickListener
    interface onCartClickListener{
        fun onCartClick(position:Int)

    }

    fun setOnCartClickListener(listener: onCartClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product,
            parent,false)
        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentCartItem = cartList[position]

        holder.cartProductName.text = currentCartItem.productName
        holder.cartProductPrice.text = currentCartItem.Price
        holder.cartProductDescription.text = currentCartItem.productDescription


    }

    override fun getItemCount(): Int {

        return cartList.size
    }


    class MyViewHolder(itemView : View, listener: onCartClickListener) : RecyclerView.ViewHolder(itemView){

        val cartProductName : TextView = itemView.findViewById(R.id.cartItemName)
        val cartProductPrice : TextView = itemView.findViewById(R.id.cartItemPrice)
        val cartProductDescription : TextView = itemView.findViewById(R.id.cartItemDescription)


        init{
            itemView.setOnClickListener {
                listener.onCartClick(adapterPosition )
            }
        }
    }

}