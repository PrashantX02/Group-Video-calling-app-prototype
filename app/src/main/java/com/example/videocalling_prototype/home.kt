package com.example.videocalling_prototype

import android.app.Application
import android.content.Intent
import android.graphics.LinearGradient
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.c
import com.google.android.play.integrity.internal.s
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.lang.ref.Reference

class home : AppCompatActivity() {
    private lateinit var logout : Button
    private lateinit var auth : FirebaseAuth
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var reference: DatabaseReference
    private lateinit var list: ArrayList<DataModel>
    private lateinit var userAdapter : RecyclerViewAdapter
    private lateinit var callButton : ZegoSendCallInvitationButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        startSystemServiceZego()

        userRecyclerView = findViewById(R.id.user_recyclerView)
        callButton = findViewById(R.id.GVCall)
        reference = FirebaseDatabase.getInstance().reference.child("user")

        userRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        list = ArrayList()

        userAdapter = RecyclerViewAdapter(this,list)
        userRecyclerView.adapter = userAdapter

        reference.addValueEventListener(object : ValueEventListener{
            @Override
            override fun onDataChange(snapshot : DataSnapshot) {
                if(snapshot.exists()){
                    list.clear()
                    snapshot.children.forEach { snap ->
                        val name : String = snap.child("name").value.toString()
                        val uid : String = snap.key.toString()
                        list.add(DataModel(name,uid))
                    }
                    userAdapter.notifyDataSetChanged()
                }
            }

            @Override
            override fun onCancelled(err: DatabaseError) {
                Log.d("Main76","${list}")
            }
        })

        callButton.setOnClickListener(View.OnClickListener {
                callButton.setIsVideoCall(true)
                callButton.setResourceID("zego_uikit_call")
                callButton.setInvitees(userAdapter.getSelectedUsers())
        })

        logout = findViewById(R.id.logout)

        logout.setOnClickListener{
            auth.signOut()
            startIntent()
        }
    }

    fun startIntent(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun startSystemServiceZego(){
        val application: Application =   application
        val appID: Long = 832828122
        val appSign: String = "1e86cdff609af279ab19e68b16cd7172382e9dea057c80e928aac761dfd3b18c"
        val userID: String =  auth.currentUser!!.uid

        FirebaseDatabase.getInstance().reference.child("user").child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userName : String ?= null
                if (snapshot.exists()) {
                    userName = snapshot.child("name").getValue(String::class.java).toString()

                    if (userName != null) {
                        Toast.makeText(applicationContext, "Welcome $userName", Toast.LENGTH_SHORT).show()
                    } else {
                        userName  = "Anonymous"
                    }

                } else {
                    Log.e("FirebaseData", "User data not found!")
                }
                val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
                ZegoUIKitPrebuiltCallService.init(application, appID, appSign, userID, userName, callInvitationConfig)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallService.unInit()
    }
}