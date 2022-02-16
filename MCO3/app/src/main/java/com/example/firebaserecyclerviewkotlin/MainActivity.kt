package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.firebaserecyclerviewkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var recyclerbtn : Button
    lateinit var favoritesbtn : Button
    lateinit var feedbackbtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Initailizing Variables
        recyclerbtn = binding.recyclerviewbtn
        favoritesbtn = binding.favourites
        feedbackbtn = binding.feedbackbtn

        //Go to Station List
        recyclerbtn.setOnClickListener {

            var i = Intent(this,StationListActivity::class.java)
            startActivity(i)
            finish()


        }
        //Go to Favorites List
        favoritesbtn.setOnClickListener(){
            var n = Intent(this, FavoritesListActivity::class.java)
            startActivity(n)
            finish()
        }
        //Go to Feedback Activity
        feedbackbtn.setOnClickListener(){
            var n = Intent(this, FeedbackActivity::class.java)
            startActivity(n)
            finish()
        }



    }
}