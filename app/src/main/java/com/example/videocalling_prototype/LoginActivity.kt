package com.example.videocalling_prototype

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var btn : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var register : TextView
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.login_button)
        email = findViewById(R.id.username)
        password = findViewById(R.id.password)
        register = findViewById(R.id.signup_link)

        register.setOnClickListener{
            val intent = Intent(this,RegistrationActivity ::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            val intent = Intent(this,home ::class.java)
            startActivity(intent)
            finish()
        }


        btn.setOnClickListener{
            val em = email.text.toString()
            val pass = password.text.toString()
            auth.signInWithEmailAndPassword(em,pass).addOnCompleteListener { task->
              if(task.isSuccessful) {
                  Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                  startTheIntent()
              }
              else  Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startTheIntent(){
        val intent = Intent(this,home ::class.java)
        startActivity(intent)
        finish()
    }
}