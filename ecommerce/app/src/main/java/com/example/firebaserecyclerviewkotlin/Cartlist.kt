package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserecyclerviewkotlin.databinding.ActivityCartlistBinding
import com.example.firebaserecyclerviewkotlin.databinding.ActivityProductlistBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.firebase.database.*
import java.lang.Exception

class Cartlist : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var cartArrayList : ArrayList<cartItem>
    private lateinit var cartRecyclerView: RecyclerView
    lateinit var binding: ActivityCartlistBinding
    private lateinit  var total:String
    private lateinit var totalTxt : TextView
    private lateinit var stationRecyclerview : RecyclerView
    private lateinit var productArrayList : ArrayList<Product>
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var cart: Button
    private var PERMISSION_ID = 1000
    private lateinit var location : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCartlistBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        totalTxt = binding.totalprice
        cartArrayList = arrayListOf<cartItem>()
        productArrayList = arrayListOf<Product>()
        cartRecyclerView = binding.cartList
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.setHasFixedSize(true)
        stationRecyclerview = binding.cartList
        stationRecyclerview.layoutManager = LinearLayoutManager(this)
        stationRecyclerview.setHasFixedSize(true)

        getItemsData()




    }

    private fun getItemsData() {

        dbref = FirebaseDatabase.getInstance("https://ecommerce-ecf39-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (stationSnapshot in snapshot.children){
                        val station = stationSnapshot.getValue(Product::class.java)
                        productArrayList.add(station!!)
                    }
                    stationRecyclerview.adapter = MyAdapter(productArrayList)

                    var adapter = MyAdapter(productArrayList)
                    stationRecyclerview.adapter = adapter

                    adapter.setOnStationClickListener(object: MyAdapter.onStationClickListener{
                        override fun onStationClick(position: Int) {
                            val model = productArrayList.get(position)

                            var productName: String? = model.productName
                            var Price: String? = model.Price
                            var productDescription: String? = model.productDescription






                        }


                    })


                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })



    }
}