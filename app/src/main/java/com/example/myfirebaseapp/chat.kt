package com.example.myfirebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirebaseapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class chat : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var messageAdapter: message_adapter
    lateinit var messageList :ArrayList<message>
    lateinit var mDbRef : DatabaseReference
    var sent :String?=null
    var recive :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        sent = receiverUid + senderUid

        recive = senderUid + receiverUid

        mDbRef = FirebaseDatabase.getInstance().getReference()

        supportActionBar?.title = name
        messageList = ArrayList()

        messageAdapter = message_adapter(this,messageList)
        binding.messageRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.messageRecyclerView.adapter = messageAdapter

        mDbRef.child("chats").child(sent!!).child("messages").
        addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (postSnapshot in snapshot.children)
                {
                    val messagee = postSnapshot.getValue(message::class.java)
                    messageList.add(messagee!!)

                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.send.setOnClickListener {

            val Message = binding.message.text.toString()

            val messageObject = message(Message,senderUid)
            mDbRef.child("chats").child(sent!!).child("messages").
            push().setValue(messageObject).addOnSuccessListener {

                mDbRef.child("chats").child(recive!!).child("messages").
                push().setValue(messageObject)
            }
            binding.message.setText("")

        }



    }
}