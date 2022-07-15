package com.example.myfirebaseapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfirebaseapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mAuth :FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    private var isRemember  = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("share_pref", Context.MODE_PRIVATE)

        isRemember = sharedPreferences.getBoolean("check",false)

        if (isRemember )
        {
            var i = Intent(this,home::class.java)
            startActivity(i)
            finish()
        }
        mAuth = FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener {
            var i = Intent(this,sign::class.java)
            startActivity(i)
            finish()
        }
        binding.login.setOnClickListener {
            var email = binding.userEmail.text.toString()
            var password = binding.password.text.toString()

            login(email,password)
        }
    }
    private fun login(email :String , password :String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.apply(){


                        putBoolean("check",true)
                    }.apply()

                    Toast.makeText(this,"Account Saved ...",Toast.LENGTH_LONG).show()

                    var i = Intent(this@MainActivity,home::class.java)
                    startActivity(i)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@MainActivity,"User dosn`t exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}