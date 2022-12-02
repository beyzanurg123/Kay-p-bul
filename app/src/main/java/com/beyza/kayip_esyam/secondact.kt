package com.beyza.kayip_esyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.beyza.kayip_esyam.databinding.ActivitySecondactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class secondact : AppCompatActivity() {
    private lateinit var binding: ActivitySecondactBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth=Firebase.auth

        val currentuser=auth.currentUser
        /*
                if(currentuser!=null){
            val intent=Intent(this,giris::class.java)
            startActivity(intent)
            finish()
        }
         */
    }
    fun girisyap(view: View){
        val email = binding.emailtext.text.toString()
        val sifre = binding.sifretext.text.toString()
        if (email.equals("") || sifre.equals("")) {
            Toast.makeText(this, "şifreni ve epostanı gir", Toast.LENGTH_LONG).show()
        }
        else {
            auth.signInWithEmailAndPassword(email, sifre).addOnSuccessListener {
                val intent = Intent(applicationContext, giris::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    fun kayitol(view: View){
        val intent= Intent(this,kayit::class.java)
        startActivity(intent)
    }
}