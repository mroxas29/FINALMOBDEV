package com.example.firebaserecyclerviewkotlin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserecyclerviewkotlin.databinding.ActivityStationlistBinding
import com.google.android.gms.location.*

import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class StationListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var stationRecyclerview : RecyclerView
    private lateinit var stationArrayList : ArrayList<Station>
    private lateinit var binding: ActivityStationlistBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var backbtn: Button
    private var PERMISSION_ID = 1000
    private lateinit var location : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStationlistBinding.inflate(layoutInflater)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)

        val view = binding.root

        setContentView(view)

        stationRecyclerview = binding.stationList
        stationRecyclerview.layoutManager = LinearLayoutManager(this)
        stationRecyclerview.setHasFixedSize(true)

        stationArrayList = arrayListOf<Station>()
        getStationData()
        RequestPermission()
        getLastLocation()

        // Search for restaurants in San Francisco
/*
        val gmmIntentUri =
            Uri.parse("geo: 14.874458,120.820154?q=restaurants")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)



*/


        backbtn = binding.backbtn

        backbtn.setOnClickListener(){
            var n = Intent(this, MainActivity::class.java)
            startActivity(n)
            finish()
        }
    }


    private fun getLastLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location:Location? = task.result
                    val intent = Intent(this@StationListActivity, stationDetail::class.java)
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        getCityName(location.latitude, location.longitude)
                        intent.putExtra("LATITUDE", location.latitude)
                        intent.putExtra("LONGITUDE", location.longitude)

                        //locationText.setText(location.latitude.toString())
                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)
        var locationText = binding.location

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        locationText.setText(cityName.toString())
        return cityName
    }



    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback,Looper.myLooper()
        )
    }
        //DITO AKO NATIGIL 20:53
//DITO AKO NATIGIL 20:53
        //DITO AKO NATIGIL 20:53//DITO AKO NATIGIL 20:53//DITO AKO NATIGIL 20:53



    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            var location = binding.location
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())



        }
    }



    private fun checkPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    private fun RequestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID
        )
    }

    private fun isLocationEnabled():Boolean{
        var locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug", "You have no permission")
            }
        }
    }



    private fun getStationData() {

        dbref = FirebaseDatabase.getInstance("https://mobdev-machine-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Stations")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (stationSnapshot in snapshot.children){
                        val station = stationSnapshot.getValue(Station::class.java)
                        stationArrayList.add(station!!)
                    }
                    stationRecyclerview.adapter = MyAdapter(stationArrayList)

                    var adapter = MyAdapter(stationArrayList)
                    stationRecyclerview.adapter = adapter

                    adapter.setOnStationClickListener(object: MyAdapter.onStationClickListener{
                        override fun onStationClick(position: Int) {
                            val model = stationArrayList.get(position)

                            var stationName: String? = model.stationname
                            var dieselPrice: String? = model.dieselPrice
                            var unleadedPrice: String? = model.unleadedPrice
                            var gasolinePrice: String? = model.gasolinePrice



                            val intent = Intent(this@StationListActivity, stationDetail::class.java)
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


