package com.example.firebaserecyclerviewkotlin

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.firebaserecyclerviewkotlin.databinding.ActivityProductDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import kotlin.properties.Delegates
import android.R
import android.view.View

import android.widget.EditText
import java.lang.Exception


class productDetail : AppCompatActivity() {
   private lateinit var binding : ActivityProductDetailBinding
    lateinit var removebtm : Button
    lateinit var addtocartbtn : Button
    lateinit var add : Button
    lateinit var minus : Button

    private lateinit var database: DatabaseReference
    private lateinit var productName:String
    private lateinit var productDescription:String
    private lateinit var price:String
    private lateinit var count:TextView
    private lateinit var quantity: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.productName.setText(intent.getStringExtra("PRODUCTNAME").toString())



        productName = intent.getStringExtra("PRODUCTNAME").toString()
        productDescription = intent.getStringExtra("PRODUCTDESCRIPTION").toString()
        price = intent.getStringExtra("PRICE").toString()

        add = binding.add
        minus = binding.minus
        count = binding.count
        try {
            var firstValue: Int = count.getText().toString().toInt()

            add.setOnClickListener(){
                firstValue += 1
                count.setText(firstValue.toString())
                intent.putExtra("QUANTITY", firstValue)
            }

            minus.setOnClickListener(){


                firstValue -= 1
                if(firstValue < 0)
                {
                    firstValue += 1
                }
                else
                {
                    count.setText(firstValue.toString())
                    intent.putExtra("QUANTITY", firstValue)
                }


            }

        } catch (e: Exception) {
            // Do something to handle the error;
        }






        addtocartbtn = binding.addtocart
        removebtm = binding.remove

        removebtm.setOnClickListener(){
            database = FirebaseDatabase.getInstance("https://ecommerce-ecf39-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart")
            database.child(productName).removeValue()

        }

        addtocartbtn.setOnClickListener(){
            database = FirebaseDatabase.getInstance("https://ecommerce-ecf39-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart")
            val product = Product(productName, productDescription,price)
            database.child(productName).setValue(product).addOnSuccessListener {
                Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener(){
                Toast.makeText(this, "Failed to Add", Toast.LENGTH_SHORT).show()
            }

        }


    }



}