package com.example.myfirebaseapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfirebaseapp.databinding.ActivitySignBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class sign : AppCompatActivity() {
    lateinit var binding: ActivitySignBinding
    lateinit var mAuth :FirebaseAuth
    lateinit var mDbRef : DatabaseReference
    lateinit var imageUri : Uri
    companion object{
        val IMAGE_CODE =100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.imageprofilselect.setOnClickListener {

            pickupgallary()
        }


        binding.btnSignUp.setOnClickListener {
            val name = binding.userNameSign.text.toString()
            val email = binding.userEmailSign.text.toString()
            val password = binding.passwordSign.text.toString()
            val phone = binding.phoneSign.text.toString()

            signup(name ,email,password , phone)




        }



    }



    private fun pickupgallary() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== IMAGE_CODE && resultCode == RESULT_OK)
        {
            imageUri = data?.data!!
            binding.imageprofilselect.setImageURI(data?.data)
        }
    }
    private fun uploadimage (imageid :String){
        if (imageUri != null)
        {
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading...")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("image/${imageid}.jpg")
            imageRef.putFile(imageUri).addOnSuccessListener { p0 ->

                pd.dismiss()
                Toast.makeText(applicationContext,"File Uploaded ...",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{p0->

                pd.dismiss()
                Toast.makeText(applicationContext,p0.message,Toast.LENGTH_SHORT).show()

            }.addOnProgressListener {p0->
                var progress = (100.0 * p0.bytesTransferred)/p0.totalByteCount
                pd.setMessage("Upload ${progress.toInt()}%")

            }
        }
    }

    private fun signup(name :String ,email :String , password :String , phone :String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                        AddUserToDatabase(name,email,mAuth.currentUser?.uid!! , password , phone)
                    uploadimage(mAuth.currentUser?.uid!!)
                    val i = Intent(this@sign,home::class.java)
                    startActivity(i)

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@sign,"some error",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun AddUserToDatabase(name:String , email :String , uid:String , password: String , phone: String)
    {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(name).setValue(User(name,email,uid , password , phone))

    }
}