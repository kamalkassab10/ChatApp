package com.example.myfirebaseapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirebaseapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import java.io.File

class home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var userlist:ArrayList<User>
    lateinit var adapter: user_adapter
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef : DatabaseReference

    lateinit var Preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        Preferences =  getSharedPreferences("share_pref", Context.MODE_PRIVATE)

        userlist = ArrayList()
        adapter = user_adapter(this,userlist)
        binding.userRv.layoutManager = LinearLayoutManager(this)
        binding.userRv.adapter = adapter
        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnapshot in snapshot.children )
                {
                    val currentuser = postSnapshot.getValue(User::class.java)

                     if (mAuth.currentUser?.uid != currentuser?.uid)
                      {
                          userlist.add(currentuser!!)


                      }else if (mAuth.currentUser?.uid == currentuser?.uid)
                     {
                         supportActionBar?.title=currentuser!!.name


                     }



                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.logout)
        {
            mAuth.signOut()
            val editor: SharedPreferences.Editor = Preferences.edit()
            editor.clear()
            editor.apply()
            val i = Intent(this@home,MainActivity::class.java)
            startActivity(i)
            finish()
            return true
        }
        return true
    }


}