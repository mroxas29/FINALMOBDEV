package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.firebaserecyclerviewkotlin.databinding.ActivityStationDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class stationDetail : AppCompatActivity() {
   private lateinit var binding : ActivityStationDetailBinding
    lateinit var gotolocationbtn : Button
    lateinit var addtoFavorites : Button
    lateinit var backtostationlist : Button
    private lateinit var database: DatabaseReference
    private lateinit var stationName:String
    private lateinit var dieselPrice:String
    private lateinit var unleadedPrice:String
    private lateinit var gasolinePrice:String
    private lateinit var longitude:String
    private lateinit var latitude:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.stationname.setText(intent.getStringExtra("STATIONNAME").toString())
        binding.dieselPrice.setText(intent.getStringExtra("DIESELPRICE").toString())
        binding.unleadedPrice.setText(intent.getStringExtra("UNLEADEDPRICE").toString())
        binding.gasolinePrice.setText(intent.getStringExtra("GASOLINEPRICE").toString())


        stationName = intent.getStringExtra("STATIONNAME").toString()
        dieselPrice = intent.getStringExtra("DIESELPRICE").toString()
        unleadedPrice = intent.getStringExtra("UNLEADEDPRICE").toString()
        gasolinePrice = intent.getStringExtra("GASOLINEPRICE").toString()
        longitude = intent.getStringExtra("LONGITUDE").toString()
        latitude = intent.getStringExtra("LATITUDE").toString()
        gotolocationbtn = binding.gotolocation

        gotolocationbtn.setOnClickListener(){
            val gmmIntentUri =
                Uri.parse("geo: $longitude,$latitude?q=$stationName")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        addtoFavorites = binding.addtofavorites
        backtostationlist = binding.backtostationlist

        backtostationlist.setOnClickListener(){
            var n = Intent(this, StationListActivity::class.java)
            startActivity(n)
            finish()
        }
        addtoFavorites.setOnClickListener(){
            database = FirebaseDatabase.getInstance("https://mobdev-machine-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Favorites")
            val station = Station(stationName, dieselPrice,unleadedPrice, gasolinePrice)
            database.child(stationName).setValue(station).addOnSuccessListener {
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener(){
                Toast.makeText(this, "Failed to Add", Toast.LENGTH_SHORT).show()
            }

        }






    }
}