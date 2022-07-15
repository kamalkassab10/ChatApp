package com.example.myfirebaseapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class user_adapter(val context: Context , val userlist :ArrayList<User>) :
    RecyclerView.Adapter<user_adapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textname = itemView.findViewById<TextView>(R.id.txt_name)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view :View = LayoutInflater.from(context).inflate(R.layout.user_layout , parent , false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userlist[position]
        holder.textname.text = currentUser.name


        holder.itemView.setOnClickListener {
            val i = Intent (context,chat::class.java)
            i.putExtra("name",currentUser.name)
            i.putExtra("uid",currentUser.uid)
            context.startActivity(i)
        }


    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}