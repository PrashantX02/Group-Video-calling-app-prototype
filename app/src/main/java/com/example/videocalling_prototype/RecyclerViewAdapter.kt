package com.example.videocalling_prototype

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.h
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Collections

class RecyclerViewAdapter(val context : Context,val list : ArrayList<DataModel>) : RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>() {

    private var selectedUsers : MutableList<ZegoUIKitUser>  = mutableListOf()

    inner class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.username)
        val checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.user,p0,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, index: Int) {
        holder.checkbox.isSelected = !holder.checkbox.isSelected
        holder.name.text = list[index].name
        val uid_user  = list[index].userUid

        if(holder.checkbox.isSelected){
            selectedUsers.add(ZegoUIKitUser(uid_user,list[index].name))
        }else{
            selectedUsers.removeAt(index)
        }

        val targetUserID: String =  uid_user // The ID of the user you want to call.
        val targetUserName: String = list[index].name // The username of the user you want to call.
        val context: Context = context // Android context.


    }
    fun getSelectedUsers(): List<ZegoUIKitUser> {
        return selectedUsers.toList()
    }
}