package com.example.firebaserecyclerviewkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.firebaserecyclerviewkotlin.databinding.ActivityFeedbackBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class FeedbackActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFeedbackBinding
    lateinit var backtoHomebtn : Button
    lateinit var sendFeedback: Button
    lateinit var feedback: TextView
    lateinit var name: TextView
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        backtoHomebtn = binding.backtoHome
        sendFeedback = binding.sendbtn
        feedback = binding.feedback
        name = binding.name

        backtoHomebtn.setOnClickListener(){
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }

        sendFeedback.setOnClickListener(){


            if(feedback.text.toString() == "" || name.text.toString() == "")
            {
                Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show()
            }else
            {
                database = FirebaseDatabase.getInstance("https://mobdev-machine-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Feedbacks")

                database.child(name.text.toString()).setValue(feedback.text.toString()).addOnSuccessListener {
                    Toast.makeText(this, "Thanks for your Feedback!", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener(){
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

                feedback.setText("")
                name.setText("")
            }


        }




    }
}