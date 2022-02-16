package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserecyclerviewkotlin.databinding.ActivityFavoritesListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*

class FavoritesListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var stationRecyclerview : RecyclerView
    private lateinit var stationArrayList : ArrayList<Station>
    private lateinit var binding: ActivityFavoritesListBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var backbtn: Button
    private var PERMISSION_ID = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)

        val view = binding.root

        setContentView(view)

        stationRecyclerview = binding.favoritesList
        stationRecyclerview.layoutManager = LinearLayoutManager(this)
        stationRecyclerview.setHasFixedSize(true)

        stationArrayList = arrayListOf<Station>()
        getStationData()

        backbtn = binding.backbtn

        //back Nav
        backbtn.setOnClickListener(){
            var n = Intent(this, MainActivity::class.java)
            startActivity(n)
            finish()
        }


    }

    private fun getStationData() {
        //Getting DB reference
        dbref = FirebaseDatabase.getInstance("https://mobdev-machine-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Favorites")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //iF data exists
                if (snapshot.exists()){
                    //Adding Data to arrayList
                    for (stationSnapshot in snapshot.children){
                        val station = stationSnapshot.getValue(Station::class.java)
                        stationArrayList.add(station!!)
                    }
                    stationRecyclerview.adapter = MyAdapter(stationArrayList)

                    var adapter = MyAdapter(stationArrayList)
                    stationRecyclerview.adapter = adapter

                    //Passing Intent for other activites
                    adapter.setOnStationClickListener(object: MyAdapter.onStationClickListener{
                        override fun onStationClick(position: Int) {
                            val model = stationArrayList.get(position)

                            var stationName: String? = model.stationname
                            var dieselPrice: String? = model.dieselPrice
                            var unleadedPrice: String? = model.unleadedPrice
                            var gasolinePrice: String? = model.gasolinePrice



                            val intent = Intent(this@FavoritesListActivity, favoriteDetail::class.java)
                            intent.putExtra("STATIONNAME", stationName)
                            intent.putExtra("DIESELPRICE", dieselPrice)
                            intent.putExtra("UNLEADEDPRICE", unleadedPrice)
                            intent.putExtra("GASOLINEPRICE", gasolinePrice)

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