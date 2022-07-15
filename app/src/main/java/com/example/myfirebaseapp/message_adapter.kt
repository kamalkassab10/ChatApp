package com.example.myfirebaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class message_adapter (val context : Context , val messageList : ArrayList<message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item_recive = 1
    val item_sent = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==1)
        {
            val view :View = LayoutInflater.from(context).inflate(R.layout.recive , parent , false)
            return reciveViewHolder(view)

        }else{
            val view :View = LayoutInflater.from(context).inflate(R.layout.sent , parent , false)
            return sendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == sendViewHolder::class.java)
        {
            //do the stuff for send holder
            val viewHolder = holder as sendViewHolder
            holder.sentmessage.text = currentMessage.messag
        }
        else{
            //do the stuff for recive holder
            val viewHolder = holder as reciveViewHolder

            holder.recivemessage.text = currentMessage.messag
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currantMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currantMessage.senderid))
        {
            return item_sent
        }else{
            return item_recive
        }
    }


    class sendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val sentmessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class reciveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val recivemessage = itemView.findViewById<TextView>(R.id.txt_recive_message)
    }

}