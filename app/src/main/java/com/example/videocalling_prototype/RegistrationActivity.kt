package com.example.videocalling_prototype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var register : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var name : EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        register = findViewById(R.id.register_button)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        name = findViewById(R.id.username);

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        register.setOnClickListener{
            val em = email.text.toString()
            val pas = password.text.toString()
            val na = name.text.toString()

            auth.createUserWithEmailAndPassword(em,pas).addOnSuccessListener {
//
                database.getReference("user").child(auth.currentUser!!.uid).child("name").setValue(na).addOnCompleteListener{
                    task ->
                    if(task.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()
                        startIntent()
                    }else{
                        auth.currentUser!!.delete()
                        Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
                    }
                }


            }.addOnFailureListener {
                Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun startIntent(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}