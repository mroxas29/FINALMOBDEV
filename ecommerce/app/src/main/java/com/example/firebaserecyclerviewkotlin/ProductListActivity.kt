package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserecyclerviewkotlin.databinding.ActivityProductlistBinding
import com.google.android.gms.location.*

import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ProductListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var stationRecyclerview : RecyclerView
    private lateinit var productArrayList : ArrayList<Product>
    private lateinit var binding: ActivityProductlistBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var cart: Button
    private var PERMISSION_ID = 1000
    private lateinit var location : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductlistBinding.inflate(layoutInflater)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)

        val view = binding.root

        setContentView(view)

        stationRecyclerview = binding.productList
        stationRecyclerview.layoutManager = LinearLayoutManager(this)
        stationRecyclerview.setHasFixedSize(true)

        productArrayList = arrayListOf<Product>()
        getItemsData()

        cart = binding.backbtn

        cart.setOnClickListener(){
            var n = Intent(this, Cartlist::class.java)
            startActivity(n)
            finish()
        }
    }

    private fun getItemsData() {

        dbref = FirebaseDatabase.getInstance("https://ecommerce-ecf39-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Products")
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
                            var test: String? = model.test



                            val intent = Intent(this@ProductListActivity, productDetail::class.java)
                            intent.putExtra("PRODUCTNAME", productName)
                            intent.putExtra("PRICE", Price)
                            intent.putExtra("PRODUCTDESCRIPTION", productDescription)
                            intent.putExtra("TEST", test)

                            startActivity(intent)

                        }


                    })


                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })



    }

}


