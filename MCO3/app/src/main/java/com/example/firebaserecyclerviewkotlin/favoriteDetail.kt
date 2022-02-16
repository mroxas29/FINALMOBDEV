package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.firebaserecyclerviewkotlin.databinding.ActivityFavoriteDetailBinding
import com.example.firebaserecyclerviewkotlin.databinding.ActivityStationDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class favoriteDetail : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteDetailBinding
    lateinit var gotolocationbtn : Button
    lateinit var removeFromFavorites : Button
    private lateinit var database: DatabaseReference
    private lateinit var stationName:String
    private lateinit var dieselPrice:String
    private lateinit var unleadedPrice:String
    private lateinit var gasolinePrice:String
    lateinit var backtofavlist : Button
    private lateinit var longitude:String
    private lateinit var latitude:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        //Setting of textviews based on data for db
        binding.stationname.setText(intent.getStringExtra("STATIONNAME").toString())
        binding.dieselPrice.setText(intent.getStringExtra("DIESELPRICE").toString())
        binding.unleadedPrice.setText(intent.getStringExtra("UNLEADEDPRICE").toString())
        binding.gasolinePrice.setText(intent.getStringExtra("GASOLINEPRICE").toString())

        //Initializing Variables
        stationName = intent.getStringExtra("STATIONNAME").toString()
        dieselPrice = intent.getStringExtra("DIESELPRICE").toString()
        unleadedPrice = intent.getStringExtra("UNLEADEDPRICE").toString()
        gasolinePrice = intent.getStringExtra("GASOLINEPRICE").toString()
        gotolocationbtn = binding.gotolocation
        longitude = intent.getStringExtra("LONGITUDE").toString()
        latitude = intent.getStringExtra("LATITUDE").toString()


        //Opening Maps with station name in queue
        gotolocationbtn.setOnClickListener(){
            val gmmIntentUri =
                Uri.parse("geo: $longitude,$latitude?q=$stationName")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        removeFromFavorites = binding.deleteFromFavorites
        backtofavlist = binding.backtofavlist

        //Back Nav
        backtofavlist.setOnClickListener(){
            var n = Intent(this, FavoritesListActivity::class.java)
            startActivity(n)
            finish()
        }

        //Remove from favourites
        removeFromFavorites.setOnClickListener(){
            database = FirebaseDatabase.getInstance("https://mobdev-machine-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Favorites")
            val station = Station(stationName, dieselPrice,unleadedPrice, gasolinePrice)
            database.child(stationName).removeValue()

            Toast.makeText(this, "Removed From Favorites", Toast.LENGTH_SHORT).show()

            var n = Intent(this, FavoritesListActivity::class.java)
            startActivity(n)
            finish()

        }

    }
}